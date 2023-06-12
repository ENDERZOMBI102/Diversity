@file:Suppress("SpellCheckingInspection", "UnstableApiUsage")
plugins {
	id("org.quiltmc.loom") version "1.0.+"
	java
}

/** Utility function that retrieves a bundle from the version catalog. */
fun Project.bundle( bundleName: String, namespace: String = name.toLowerCase() ): Provider<ExternalModuleDependencyBundle> =
	rootProject.extensions.getByType<VersionCatalogsExtension>()
		.named("libs")
		.findBundle( "$namespace.$bundleName" )
		.get()

/** Utility function that retrieves a version from the version catalog. */
fun version( key: String ): String =
	rootProject.extensions.getByType<VersionCatalogsExtension>()
		.named( "libs" )
		.findVersion( key )
		.get()
		.requiredVersion

allprojects {
	apply( plugin="org.quiltmc.loom" )
	loom.runtimeOnlyLog4j.set( true )

	repositories {
		mavenCentral()
		maven( "https://jitpack.io" )
		maven( "https://maven.gegy.dev" )
		maven( "https://maven.shedaniel.me" )
		maven( "https://maven.wispforest.io" )
		maven( "https://maven.ryanliptak.com" )
		maven( "https://maven.draylar.dev/releases" )
		maven( "https://repsy.io/mvn/enderzombi102/mc" )
		maven( "https://maven.terraformersmc.com/releases" )
		maven( "https://maven.quiltmc.org/repository/release" )
		maven( "https://maven.quiltmc.org/repository/snapshot" )
		maven( "https://server.bbkr.space/artifactory/libs-release" )
	}

	dependencies {
		minecraft( "com.mojang:minecraft:${version("minecraft")}" )
		mappings( "org.quiltmc:quilt-mappings:${version("minecraft")}+build.${version("mappings")}:intermediary-v2" )

		implementation( bundle( "implementation", "common" ) )
		modImplementation( bundle( "mod.compileapi", "common" ) )
		modImplementation( bundle( "mod.implementation", "common" ) )
	}
}

val core = project(":Core")
subprojects {
	dependencies {
		// Common dependencies
		implementation( bundle( "implementation", "common" ) )
		modCompileOnlyApi( bundle( "mod.compileapi", "common" ) )
		modImplementation( bundle( "mod.implementation", "common" ) )

		// Module-specific dependencies
		include( bundle( "include" ) )
		implementation( bundle( "implementation" ) )
		annotationProcessor( bundle( "annotation" ) )
		modImplementation( bundle( "mod.implementation" ) )
		modCompileOnlyApi( bundle( "mod.compileapi" ) )

		if ( project != core )
			compileOnly( project( ":Core", "namedElements" ) )
	}

	tasks.withType<ProcessResources> {
		inputs.property( "version"          , version )
		inputs.property( "loader_version"   , version("loader") )
		inputs.property( "minecraft_version", version("minecraft") )
		inputs.property( "core_version"     , core.version )
		filteringCharset = "UTF-8"

		var deps = """
		{ "id": "quilt_loader", "versions": "${version("loader")}" },
		{ "id": "quilt_base", "versions": "*" },
		{ "id": "minecraft", "versions": ">=${version("minecraft")}" },
		{ "id": "java", "versions": ">=17" }
		""".trimIndent()
		if ( project != core )
			deps += ",\n{ \"id\": \"diversity-core\", \"versions\": \"${core.version}\" }"

		filesMatching( "quilt.mod.json" ) {
			expand(
				"version"      to version,
				"group"        to "com.enderzombi102.diversity",
				"dependencies" to deps
			)
			filter { it.substringBefore("///") }
		}
	}

	tasks.withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.release.set( 17 )
	}

	java.toolchain.languageVersion.set( JavaLanguageVersion.of( 17 ) )

	tasks.withType<AbstractArchiveTask> {
		archiveBaseName.set( project.name.toLowerCase() )
		destinationDirectory.set( rootProject.buildDir.resolve( if ( archiveClassifier.get() in listOf( "dev", "sources" ) ) "devlibs" else "libs" ) )
	}

	tasks.withType<Jar> {
		from( "LICENSE" ) {
			rename { "${it}_$archiveBaseName}" }
		}
	}

	rootProject.dependencies {
		include( implementation( project( path=path, configuration="namedElements" ) )!! )
		// add this module's dependencies to the runtime classpath
		implementation( bundle( "implementation" ) )
		modImplementation( bundle( "mod.implementation" ) )
		modImplementation( bundle( "mod.compileapi" ) )
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
