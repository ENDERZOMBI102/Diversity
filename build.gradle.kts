@file:Suppress("SpellCheckingInspection", "UnstableApiUsage")
import net.fabricmc.loom.api.mappings.layered.spec.LayeredMappingSpecBuilder

plugins {
	id("org.quiltmc.loom") version "0.12.+"
	java
}

/** Utility function that retrieves a bundle from the version catalog. */
fun bundle( proj: String, bundleName: String ) =
	libs.findBundle( "${proj.toLowerCase()}.$bundleName" ).get()

/** Utility function that retrieves a version from the version catalog. */
fun version( name: String ) = libs.findVersion( name ).get().displayName

fun LayeredMappingSpecBuilder.quilt( minecraft: String, mappings: String ) =
	addLayer( quiltMappings.mappings( "org.quiltmc:quilt-mappings:$minecraft+build.$mappings:v2" ) )

operator fun File.div(path: String ) = this.resolve( path )

allprojects {
	apply( plugin="org.quiltmc.loom" )
	loom.shareRemapCaches.set( true )
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
		mappings( loom.layered { quilt( version("minecraft"), version("mappings") ) } )
	}
}

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

		if ( name != "Core" )
			compileOnly( project( ":Core", "namedElements" ) )
	}

	tasks.withType<ProcessResources>().configureEach {
		inputs.property( "version"          , version )
		inputs.property( "group"            , "com.enderzombi102.diversity" )
		inputs.property( "loader_version"   , rootProject.libs.versions.loader.get() )
		inputs.property( "minecraft_version", libs.versions.minecraft.get() )
		filteringCharset = "UTF-8"

		filesMatching( "quilt.mod.json" ) {
			expand(
				Pair( "version"          , version ),
				Pair( "group"            , "com.enderzombi102.diversity" ),
				Pair( "loader_version"   , rootProject.libs.versions.loader.get() ),
				Pair( "minecraft_version", libs.versions.minecraft.get() ),
			)
		}
	}

	tasks.withType<JavaCompile>().configureEach {
		options.encoding = "UTF-8"
		options.release.set( 17 )
	}

	java {
		toolchain.languageVersion.set( JavaLanguageVersion.of( 17 ) )
		withSourcesJar()
	}

	tasks.withType<AbstractArchiveTask>().configureEach {
		archiveBaseName.set( project.name.toLowerCase() )
		destinationDirectory.set( rootProject.buildDir / ( if ( archiveClassifier.get() in listOf( "dev", "sources" ) ) "devlibs" else "libs" ) )
	}

	tasks.withType<Jar>().configureEach {
		from( "LICENSE" ) {
			rename { "${it}_${project.ext["archivesBaseName"]}" }
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

tasks.withType<ProcessResources>().configureEach {
	inputs.property( "version", version )
	inputs.property( "group", "com.enderzombi102" )
	filteringCharset = "UTF-8"

	filesMatching("quilt.mod.json") {
		expand( Pair( "version", version ), Pair( "group", "com.enderzombi102" ) )
	}
}
