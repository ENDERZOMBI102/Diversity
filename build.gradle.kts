@file:Suppress("UnstableApiUsage")

plugins {
	java
	id( "org.quiltmc.loom" ).version( "0.12.+" )
}

val quilted_fapi_version: String by project
val clothconfig_version: String by project
val annotations_version: String by project
val minecraft_version: String by project
val enderlib_version: String by project
val modmenu_version: String by project
val quilt_mappings: String by project
val loader_version: String by project
val dawn_version: String by project
val qsl_version: String by project

allprojects {
	repositories {
		mavenCentral()
		maven( url="https://jitpack.io" )
		maven( url="https://maven.shedaniel.me" )
		maven( url="https://maven.ryanliptak.com" )
		maven( url="https://repsy.io/mvn/enderzombi102/mc" )
		maven( url="https://maven.terraformersmc.com/releases" )
		maven( url="https://maven.quiltmc.org/repository/release" )
		maven( url="https://maven.quiltmc.org/repository/snapshot" )
	}
}

subprojects {
	apply {
		plugin("org.quiltmc.loom")
	}

	val archivesBaseName: String by project

	dependencies {
		minecraft( "com.mojang:minecraft:$minecraft_version" )
		mappings(loom.layered {
			addLayer( quiltMappings.mappings("org.quiltmc:quilt-mappings:$minecraft_version+build.$quilt_mappings:v2") )
		})
		modImplementation( "org.quiltmc:quilt-loader:$loader_version" )
		modImplementation( "org.quiltmc:qsl:$qsl_version+$minecraft_version" )
		modImplementation( "org.quiltmc.quilted-fabric-api:quilted-fabric-api:$quilted_fapi_version-$minecraft_version" )

		/* mod dependencies */

		// cloth config
		modImplementation( "me.shedaniel.cloth:cloth-config-fabric:$clothconfig_version" ) {
			transitive( false )
		}
		// modmenu
		modImplementation( "com.terraformersmc:modmenu:$modmenu_version" ) {
			transitive( false )
		}
		modApi( "com.github.DawnTeamMC:DawnAPI:v$dawn_version" )

		/* lib dependencies */
		implementation( "com.enderzombi102:EnderLib:$enderlib_version" )

		implementation( "org.jetbrains:annotations:$annotations_version" )

//		if ( project.name != "Core" ) {
//			compileOnly( project( path: ":Core" ) ) {
//				transitive = false
//			}
//		}
	}

	processResources {
		inputs.property( "version", version )
		inputs.property( "group", group )
		inputs.property( "loader_version", loader_version )
		inputs.property( "minecraft_version", minecraft_version )
		inputs.property( "cc_version", clothconfig_version )
		filteringCharset( "UTF-8" )

		filesMatching("quilt.mod.json") {
			expand(
				mapOf(
					Pair( "version", version ),
					Pair( "group", group ),
					Pair( "loader_version", loader_version ),
					Pair( "minecraft_version", minecraft_version ),
					Pair( "cc_version", clothconfig_version )
				)
			)
		}
	}

	tasks.withType(JavaCompile).configureEach {
		it.options.encoding = "UTF-8"
		it.options.release.set(17)
	}

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
		withSourcesJar()
	}

	jar {
		from("LICENSE") {
			rename { "${it}_$archivesBaseName" }
		}
	}
}

dependencies {
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( loom.layered {
		addLayer( quiltMappings.mappings( "org.quiltmc:quilt-mappings:$minecraft_version+build.$quilt_mappings:v2" ) )
	} )

//	include implementation( project( ":Core" ) )
//	include implementation( project( ":Flora" ) )
}

processResources {
	inputs.property( "version", version )
	inputs.property( "group", group )
	filteringCharset( "UTF-8" )

	filesMatching("quilt.mod.json") {
		expand(
			mapOf(
				Pair( "version", version ),
				Pair( "group", group )
			)
		)
	}
}
