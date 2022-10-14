plugins {
	kotlin("jvm") version "1.7.20"
}

repositories {
	mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}