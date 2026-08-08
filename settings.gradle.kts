pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.isxander.dev/releases")
    }
}

rootProject.name = "configurable-difficulty"

include("common")
include("fabric")
include("neoforge")
