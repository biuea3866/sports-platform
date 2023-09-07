rootProject.name = "sports-platform"

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings

    val kotlinJvmPluginVersion: String by settings
    val jibPluginVersion: String by settings
    val detektPluginVersion: String by settings

    val kotlinPluginVersion: String by settings
    val detektVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val kaptVersion: String by settings

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.spring.io/release")
        maven("https://repo.spring.io/milestone/")
        maven("https://jitpack.io")
//        maven {
//            url =
//                uri("https://maven.pkg.github.com/doodlincorp/maven-artifact")
//            credentials {
//                username = System.getenv("GITHUB_USERNAME")
//                password = System.getenv("GITHUB_TOKEN")
//            }
//        }
    }

    plugins {
        jacoco

        kotlin("jvm") version kotlinJvmPluginVersion
        kotlin("plugin.spring") version kotlinJvmPluginVersion
        kotlin("plugin.jpa") version kotlinJvmPluginVersion
        kotlin("kapt") version kaptVersion

        id("org.jetbrains.kotlin.plugin.noarg") version kotlinJvmPluginVersion
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinJvmPluginVersion

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("io.gitlab.arturbosch.detekt") version detektPluginVersion

        id("com.google.cloud.tools.jib") version jibPluginVersion
    }
}

include(":core")
include(":api-server")

project(":core").projectDir = file("core")
project(":api-server").projectDir = file("api-server")

buildCache {
    local {
        directory = "${gradle.gradleUserHomeDir}/caches"
    }
}
