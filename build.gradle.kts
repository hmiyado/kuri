import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.plugins.ExtensionAware

import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension
import java.net.URI

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.51"

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlin_version))
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0")
    }
}

group = "com.github.hmiyado"
version = "1.0-SNAPSHOT"

plugins {
    java
}

apply {
    plugin("kotlin")
    plugin("org.junit.platform.gradle.plugin")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
    maven { url = URI.create("http://dl.bintray.com/jetbrains/spek") }
}

configure<JUnitPlatformExtension> {
    filters {
        engines {
            include("spek")
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8", kotlin_version))

    testImplementation("org.jetbrains.spek:spek-api:1.1.5")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.7")

    testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
}
// extension for configuration
fun JUnitPlatformExtension.filters(setup: FiltersExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(FiltersExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}

fun FiltersExtension.engines(setup: EnginesExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(EnginesExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}