package com.example.biomediff;

import com.example.biomediff.config.BiomeConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiomeDifficulty {
    private static final Logger LOGGER = LoggerFactory.getLogger("BiomeDifficulty");
    private static BiomeConfig config;
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        BiomeDifficulty.plugin = plugin;
        loadConfig();
    }

    public static void loadConfig() {
        config = new BiomeConfig(plugin);
        config.load();
        LOGGER.info("Configuration loaded successfully");
    }

    public static BiomeConfig getConfig() {
        return config;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}