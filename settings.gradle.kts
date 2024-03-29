@file:Suppress("UnstableApiUsage")
enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven( url="https://maven.fabricmc.net" )
		maven( url="https://maven.quiltmc.org/repository/release" )
		maven( url="https://maven.quiltmc.org/repository/snapshot" )
		maven( url="https://server.bbkr.space/artifactory/libs-release" )
	}
}
rootProject.name = "Diversity"

include( "Core" )
include( "Flora" )
include( "Mineralogy" )
