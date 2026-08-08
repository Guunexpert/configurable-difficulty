plugins {
    id("net.neoforged.moddev") version "2.0.143"
}

val minecraftVersion: String = property("minecraft_version").toString()
val neoforgeVersion: String = property("neoforgeVersion").toString()
val modId: String = property("mod_id").toString()
val modName: String = property("modName").toString()
val modAuthor: String = property("mod_author").toString()

base {
    archivesName.set("configurable-difficulty-neoforge")
}

dependencies {
    implementation(project(":common"))

    implementation("dev.isxander:yet-another-config-lib:3.8.1+1.21.11-neoforge")
}

neoForge {
    enable {
        setVersion(neoforgeVersion)
        setDisableRecompilation(true)
    }
    validateAccessTransformers = true

    setAccessTransformers("$rootDir/common/src/main/resources/META-INF/accesstransformer.cfg")

    runs {
        configureEach {
            systemProperty("forge.logging.console.level", "debug")
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
            sourceSet(project(":common").sourceSets.main.get())
        }
    }
}

tasks.named<ProcessResources>("processResources") {
    val expandProps = mapOf(
        "version" to project.version,
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_author" to modAuthor,
        "minecraft_version" to minecraftVersion
    )

    inputs.properties(expandProps)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(expandProps)
    }
}

tasks.jar {
    from(project(":common").sourceSets.main.get().output)
}
