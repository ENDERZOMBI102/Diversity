package com.enderzombi102.diversity.core.registry

import com.enderzombi102.diversity.core.Core
import com.enderzombi102.diversity.core.Core.Companion.LOGGER
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.jetbrains.annotations.ApiStatus.Internal
import org.quiltmc.qsl.item.setting.api.CustomItemSetting
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings

object Registries {
	@JvmField
	val BLOCKS = registry<Block>( Registries.BLOCK )
	@JvmField
	val ITEMS = registry<Item>( Registries.ITEM )

	fun register() {
		BLOCKS.registerInternal()
		ITEMS.registerInternal()

		ITEMS.contents.asSequence()
			.map { (key, item) -> key to item }
	}

	private inline fun <reified T> registry( reg: Registry<T> ) =
		DiversityRegistry( reg, T::class.java.simpleName.lowercase() )

	object ItemUtil {
		internal val GROUP: CustomItemSetting<String> = CustomItemSetting.create { null }

		fun QuiltItemSettings.group( name: String ): QuiltItemSettings {
			this.customSetting( GROUP, name )
			return this
		}
	}

	class DiversityRegistry<T>( private val reg: Registry<T>, private val type: String ) {
		internal val contents: MutableMap<Identifier, T> = HashMap()

		fun register( name: String, obj: T ): T {
			contents[ Identifier( Core.currentId, name ) ] = obj
			return obj
		}

		@Internal
		internal fun registerInternal() {
			LOGGER.info( "Registering ${type}s" )
			for ( ( id, obj ) in contents )
				Registry.register( reg, id, obj )
		}

		fun getOrDefault( id: Identifier, default: T ): T =
			contents.getOrDefault( id, default )
	}
}
