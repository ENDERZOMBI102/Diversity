@file:Suppress("PropertyName", "UnstableApiUsage", "SpellCheckingInspection")
plugins {
	id("org.quiltmc.loom").version( "0.12.+" )
	java
}

val group: String by project
val version: String by project
val archivesBaseName: String by project

val minecraft_version = libs.versions.minecraft.get()
val loader_version = libs.versions.loader.get()
val mappings = libs.versions.mappings.get()

allprojects {
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
}

/**
 * Utility function that retrieves a bundle from the version catalog
 */
fun bundle( proj: String, bundleName: String ) =
	libs.findBundle( "${proj.toLowerCase()}.$bundleName" ).get()

subprojects {
	apply( plugin="org.quiltmc.loom" )

	loom.shareRemapCaches.set( true )
	loom.runConfigs["client"].runDir = "../run"
	loom.runConfigs["server"].runDir = "../run"

	dependencies {
		minecraft( "com.mojang:minecraft:$minecraft_version" )
		mappings( loom.layered {
			addLayer( quiltMappings.mappings( "org.quiltmc:quilt-mappings:$minecraft_version+build.$mappings:v2" ) )
		})

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
		inputs.property( "version", version )
		inputs.property( "group", "com.enderzombi102.diversity" )
		inputs.property( "loader_version", loader_version )
		inputs.property( "minecraft_version", minecraft_version )
		filteringCharset = "UTF-8"

		filesMatching( "quilt.mod.json" ) {
			expand(
				Pair( "version"          , version ),
				Pair( "group"            , "com.enderzombi102.diversity" ),
				Pair( "loader_version"   , loader_version ),
				Pair( "minecraft_version", minecraft_version ),
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
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( loom.layered {
		addLayer( quiltMappings.mappings( "org.quiltmc:quilt-mappings:$minecraft_version+build.$mappings:v2" ) )
	})
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
