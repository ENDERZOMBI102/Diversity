plugins {
	kotlin("jvm") version "1.7.20"
}

dependencies {
	implementation( kotlin( "stdlib-jdk17" ) )
}

repositories {
	mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}