package com.enderzombi102.diversity.core.module

import com.enderzombi102.diversity.core.Core
import com.enderzombi102.enderlib.collections.ListUtil.append
import com.enderzombi102.enderlib.io.FileUtils.toPath
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.QuiltLoader
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import java.io.FileNotFoundException
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists

@Suppress("UNCHECKED_CAST")
object DiversityModules {
	private val MODULES: MutableMap<String, ModuleData> = HashMap()

	@JvmStatic
	fun getModule(name: String): ModuleData {
		require(MODULES.containsKey(name)) {
			"No module called `$name` is installed! why has it been requested?"
		}
		return MODULES[name]!!
	}

	/**
	 * Searches a file/folder path in all the module's root paths, if found return it, else throw [FileNotFoundException].
	 * @param file file to search for.
	 * @return the path to the file.
	 * @throws FileNotFoundException if not found.
	 */
	@Throws(FileNotFoundException::class)
	@JvmStatic
	fun find(file: String): Path = MODULES.values
		.stream()
		.flatMap(ModuleData::streamRoots)
		.map { it.resolve(file) }
		.filter(Path::exists)
		.findFirst()
		.orElseThrow { FileNotFoundException("File `$file` not found in any root of any diversity module!") }


	@JvmStatic
	fun construct() {
		val objects = QuiltLoader.getAllMods()
			.stream()
			.map { Pair( it, it.metadata() ) }
			.filter { it.second.containsValue( "diversity" ) }
			.map { Pair( it.first, it.second.value("diversity")!!.asObject() ) }
			.toList()

		for (entry in objects) {
			val name = entry.second["name"]?.asString() ?: entry.first.metadata().id().substringAfter("-")
			val main = entry.second["main"]?.asString() ?: "com.enderzombi102.diversity.$name.${ name.replaceFirstChar { it.uppercase() } }"
			val client = entry.second["client"]?.asString() ?: "${main}Client"

			val clazz = Class.forName( main ) as Class<out ModInitializer?>
			val clientClazz = try {
					Class.forName( client, true, clazz.classLoader )
				} catch ( e: ClassNotFoundException ) {
					null
				} as Class<out ClientModInitializer>?

			MODULES[name] = ModuleData(
				name,
				buildList {
					val sources = toPath(clazz.protectionDomain.codeSource.location)
					append(
						this,
						sources,
						entry.first.getPath("assets").parent
					)
					val kotlin = sources.parent.parent.resolve("kotlin/main")
					if ( kotlin.toFile().exists() )
						add( kotlin )
				},
				entry.first,
				Initializer( clazz, clazz.constructors[0].newInstance() as ModInitializer ),
				if ( clientClazz != null )
					Initializer( clientClazz, clientClazz.constructors[0].newInstance() as ClientModInitializer )
				else
					null
			)
		}

		val meta = QuiltLoader.getModContainer("diversity-core").orElseThrow()
		MODULES["core"] = ModuleData(
			"core",
			buildList {
				val sources = toPath( Core::class.java.protectionDomain.codeSource.location )
				append(
					this,
					sources,
					meta.getPath("assets").parent
				)
				val kotlin = sources.parent.parent.resolve("kotlin/main")
				if ( kotlin.toFile().exists() )
					add( kotlin )
			},
			meta,
			null,
			null
		)
	}

	@JvmStatic
	fun modules() = this.MODULES.values as Collection<ModuleData>
}

/**
 * Represents a diversity module's data.
 * @param name name of the module.
 * @param roots classes and resources roots, may differ.
 * @param main main class
 * @param client client class, if exists.
 */
@JvmRecord
data class ModuleData(
	val name: String,
	val roots: List<Path>,
	val container: ModContainer,
	val main: Initializer<ModInitializer>?,
	val client: Initializer<ClientModInitializer>?
) {
	/**
	 * Searches a file/folder path in all the module's root paths, if found return it, else throw [FileNotFoundException].
	 * @param file file to search for.
	 * @return the path to the file.
	 * @throws FileNotFoundException if not found.
	 */
	@Throws(FileNotFoundException::class)
	fun find(file: String): Path = roots.stream()
		.map { it.resolve(file) }
		.filter(Path::exists)
		.findFirst()
		.orElseThrow { FileNotFoundException("File `$file` not found in any of `diversity-$name`'s roots!") }

	/**
	 * Creates a stream of this module's roots.
	 * @return a stream of the roots.
	 */
	fun streamRoots() = this.roots.stream()
}

data class Initializer<T>(val clazz: Class<out T>, var instance: T )
