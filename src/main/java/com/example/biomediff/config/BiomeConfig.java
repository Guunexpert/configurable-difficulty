package com.example.biomediff.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class BiomeConfig {
    public boolean enabled = true;
    public ModifierMode playerMode = ModifierMode.DYNAMIC;
    public ModifierMode mobMode = ModifierMode.SPAWN_ONLY;
    public int checkInterval = 20;
    public boolean applyToHostileMobs = true;
    public boolean applyToPassiveMobs = false;
    public boolean applyToNeutralMobs = false;
    public boolean xpEnabled = false;
    public boolean debugEnabled = false;
    public boolean debugLogBiomeChanges = true;
    public boolean debugLogAttributeChanges = true;
    public AttributeEnabledConfig enabledAttributes = new AttributeEnabledConfig();
    public BiomeMultipliers defaultMultipliers = new BiomeMultipliers();
    public Map<String, BiomeMultipliers> dimensionMultipliers = new HashMap<>();
    public Map<String, BiomeMultipliers> biomeMultipliers = new HashMap<>();
    public DepthScalingConfig depthScaling = new DepthScalingConfig();

    private final JavaPlugin plugin;

    public BiomeConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        var config = plugin.getConfig();

        enabled = config.getBoolean("enabled", enabled);
        playerMode = ModifierMode.fromString(config.getString("player-mode", playerMode.name()));
        mobMode = ModifierMode.fromString(config.getString("mob-mode", mobMode.name()));
        checkInterval = config.getInt("check-interval", checkInterval);
        applyToHostileMobs = config.getBoolean("apply-to-hostile-mobs", applyToHostileMobs);
        applyToPassiveMobs = config.getBoolean("apply-to-passive-mobs", applyToPassiveMobs);
        applyToNeutralMobs = config.getBoolean("apply-to-neutral-mobs", applyToNeutralMobs);
        xpEnabled = config.getBoolean("xp-enabled", xpEnabled);
        debugEnabled = config.getBoolean("debug-enabled", debugEnabled);
        debugLogBiomeChanges = config.getBoolean("debug-log-biome-changes", debugLogBiomeChanges);
        debugLogAttributeChanges = config.getBoolean("debug-log-attribute-changes", debugLogAttributeChanges);
        enabledAttributes = AttributeEnabledConfig.fromSection(config.getConfigurationSection("enabled-attributes"));
        defaultMultipliers = BiomeMultipliers.fromSection(config.getConfigurationSection("default-multipliers"));
        depthScaling = DepthScalingConfig.fromSection(config.getConfigurationSection("depth-scaling"));

        dimensionMultipliers.clear();
        ConfigurationSection dimSection = config.getConfigurationSection("dimension-multipliers");
        if (dimSection != null) {
            for (String key : dimSection.getKeys(false)) {
                dimensionMultipliers.put(key, BiomeMultipliers.fromSection(dimSection.getConfigurationSection(key)));
            }
        }

        biomeMultipliers.clear();
        ConfigurationSection biomeSection = config.getConfigurationSection("biome-multipliers");
        if (biomeSection != null) {
            for (String key : biomeSection.getKeys(false)) {
                biomeMultipliers.put(key, BiomeMultipliers.fromSection(biomeSection.getConfigurationSection(key)));
            }
        }
    }

    public BiomeMultipliers getMultipliersForBiome(String biomeId) {
        return biomeMultipliers.getOrDefault(biomeId, defaultMultipliers);
    }
}