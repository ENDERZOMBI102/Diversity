@file:Suppress("SpellCheckingInspection", "UnstableApiUsage")
plugins {
	id("org.quiltmc.loom") version "1.0.+"
	java
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

/** Utility function that retrieves a bundle from the version catalog. */
fun bundle( proj: String, bundleName: String ) =
	libs.findBundle( "${proj.toLowerCase()}.$bundleName" ).get()

/** Utility function that retrieves a version from the version catalog. */
fun version( key: String ) = libs.findVersion(key).get().requiredVersion

operator fun File.div(path: String ) = this.resolve( path )

allprojects {
	apply( plugin="org.quiltmc.loom" )
	loom.runtimeOnlyLog4j.set( true )

	repositories {
		mavenLocal()
		mavenCentral()
		maven( "https://jitpack.io" )
		maven( "https://maven.gegy.dev" )
		maven( "https://maven.shedaniel.me" )
		maven( "https://maven.ryanliptak.com" )
		maven( "https://repsy.io/mvn/enderzombi102/mc" )
		maven( "https://maven.terraformersmc.com/releases" )
		maven( "https://maven.quiltmc.org/repository/release" )
		maven( "https://maven.quiltmc.org/repository/snapshot" )
	}

	dependencies {
		minecraft( "com.mojang:minecraft:${version("minecraft")}" )
		mappings( "org.quiltmc:quilt-mappings:${version("minecraft")}+build.${version("mappings")}:v2" )

		implementation( bundle( "common", "implementation" ) )
		modImplementation( bundle( "common", "mod.compileapi" ) )
		modImplementation( bundle( "common", "mod.implementation" ) )
	}
}

val core = project(":Core")
subprojects {
	dependencies {
		// Common dependencies
		implementation( bundle( "common", "implementation" ) )
		modCompileOnlyApi( bundle( "common", "mod.compileapi" ) )
		modImplementation( bundle( "common", "mod.implementation" ) )

		// Module-specific dependencies
		include( bundle( name, "include" ) )
		implementation( bundle( name, "implementation" ) )
		modImplementation( bundle( name, "mod.implementation" ) )
		modCompileOnlyApi( bundle( name, "mod.compileapi" ) )

		if ( project != core )
			compileOnly( project( ":Core", "namedElements" ) )
	}

	tasks.withType<ProcessResources> {
		inputs.property( "version"          , version )
		inputs.property( "loader_version"   , version("loader") )
		inputs.property( "minecraft_version", version("minecraft") )
		inputs.property( "core_version", core.version )
		filteringCharset = "UTF-8"

		filesMatching( "quilt.mod.json" ) {
			expand(
				"version"      to version,
				"group"        to "com.enderzombi102.diversity",
				"dependencies" to """
				{ "id": "quilt_loader", "versions": "${version("loader")}" },
				{ "id": "quilt_base", "versions": "*" },
				{ "id": "minecraft", "versions": ">=${version("minecraft")}" },
				{ "id": "java", "versions": ">=17" }${ if ( project == core ) "" else """,
				{ "id": "diversity-core", "versions": "${core.version}" }""".trimIndent() }""".trimIndent()
			)
			filter { it.substringBefore("///") }
		}
	}

	tasks.withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.release.set( 17 )
	}

	java {
		toolchain.languageVersion.set( JavaLanguageVersion.of( 17 ) )
		withSourcesJar()
	}

	tasks.withType<AbstractArchiveTask> {
		archiveBaseName.set( project.name.toLowerCase() )
		destinationDirectory.set( rootProject.buildDir / ( if ( archiveClassifier.get() in listOf( "dev", "sources" ) ) "devlibs" else "libs" ) )
	}

	tasks.withType<Jar> {
		from( "LICENSE" ) {
			rename { "${it}_$archiveBaseName}" }
		}
	}

	rootProject.dependencies {
		include( implementation( project( path=path, configuration="namedElements" ) )!! )
		// add this module's dependencies to the runtime classpath
		implementation( bundle( name, "implementation" ) )
		modImplementation( bundle( name, "mod.implementation" ) )
		modImplementation( bundle( name, "mod.compileapi" ) )
	}
}

loom.runConfigs["client"].isIdeConfigGenerated = true
loom.runConfigs["server"].isIdeConfigGenerated = true

tasks.withType<ProcessResources> {
	inputs.property( "version", version )
	filteringCharset = "UTF-8"

	filesMatching("quilt.mod.json") {
		expand( "version" to version, "group" to "com.enderzombi102" )
	}
}
