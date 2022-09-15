@file:Suppress("SpellCheckingInspection", "UnstableApiUsage")

import net.fabricmc.loom.api.mappings.layered.spec.LayeredMappingSpecBuilder
import net.fabricmc.loom.task.RemapSourcesJarTask

plugins {
	id("org.quiltmc.loom").version( "0.12.+" )
	java
}

val group: String by project
val version: String by project
val archivesBaseName: String by project

allprojects {
	repositories {
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
}

/**
 * Utility function that retrieves a bundle from the version catalog
 */
fun bundle( proj: String, bundleName: String ) =
	libs.findBundle( "${proj.toLowerCase()}.$bundleName" ).get()

/**
 * Utility function that retrieves a version from the version catalog
 */
fun version( name: String ) = libs.findVersion( name ).get().displayName

/**
 * Basically to make the mappings an oneliner.
 */
fun LayeredMappingSpecBuilder.quilt( minecraftVersion: String, mappingsVersion: String ) =
	addLayer( quiltMappings.mappings( "org.quiltmc:quilt-mappings:$minecraftVersion+build.$mappingsVersion:v2" ) )

subprojects {
	apply( plugin="org.quiltmc.loom" )

	loom.shareRemapCaches.set( true )
	loom.runConfigs["client"].runDir = "../run"
	loom.runConfigs["server"].runDir = "../run"

	dependencies {
		minecraft( "com.mojang:minecraft:${version("minecraft")}" )
		mappings( loom.layered { quilt( version("minecraft"), version("mappings") ) } )

		// Common dependencies
		implementation( bundle( "common", "implementation" ) )
		modCompileOnlyApi( bundle( "common", "mod.compileapi" ) )
		modImplementation( bundle( "common", "mod.implementation" ) )

		// Project-specific dependencies
		include( bundle( name, "include" ) )
		implementation( bundle( name, "implementation" ) )
		modImplementation( bundle( name, "mod.implementation" ) )
		modCompileOnlyApi( bundle( name, "mod.compileapi" ) )

		if ( name != "Core" ) {
			compileOnly( project( ":Core", "namedElements" ) )
		}
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
		toolchain {
			languageVersion.set( JavaLanguageVersion.of( 17 ) )
		}
		withSourcesJar()
	}

	tasks.withType<AbstractArchiveTask>().configureEach {
		archiveBaseName.set( project.name.toLowerCase() )
		destinationDirectory.set(
			rootProject.buildDir.resolve(
				when ( archiveClassifier.get() ) {
					"dev" -> "devlibs"
					"sources" -> if ( this is RemapSourcesJarTask ) "libs" else "devlibs"
					else -> "libs"
				}
			)
		)
	}

	tasks.withType<Jar>().configureEach {
		from( "LICENSE" ) {
			rename { "${it}_$archivesBaseName" }
		}
	}
}

loom.shareRemapCaches.set( true )
loom.runConfigs["client"].isIdeConfigGenerated = true
loom.runConfigs["server"].isIdeConfigGenerated = true

dependencies {
	minecraft( "com.mojang:minecraft:${version("minecraft")}" )
	mappings( loom.layered { quilt( version("minecraft"), version("mappings") ) } )
	runtimeOnly( include( project( path=":Core", configuration="namedElements" ) )!! )
	runtimeOnly( include( project( path=":Flora", configuration="namedElements" ) )!! )

	for ( proj in subprojects ) {
		runtimeOnly( bundle( proj.name, "implementation" ) )
		modRuntimeOnly( bundle( proj.name, "mod.implementation" ) )
		modRuntimeOnly( bundle( proj.name, "mod.compileapi" ) )
	}
}

tasks.withType<ProcessResources>().configureEach {
	inputs.property( "version", version )
	inputs.property( "group", "com.enderzombi102" )
	filteringCharset = "UTF-8"

	filesMatching("quilt.mod.json") {
		expand(
			Pair( "version", version ),
			Pair( "group", "com.enderzombi102" )
		)
	}
}
