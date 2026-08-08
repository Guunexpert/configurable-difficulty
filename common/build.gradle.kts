plugins {
    id("java-library")
    id("net.fabricmc.fabric-loom-remap") version "1.17.19"
}

val minecraftVersion = property("minecraft_version").toString()

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())

    compileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.11-fabric")
}
