package com.example.biomediff.config;

import org.bukkit.configuration.ConfigurationSection;

public class DepthScalingConfig {
    public boolean enabled = false;
    public int yThreshold = 0;
    public int maxDepth = -64;
    public String scalingMode = "linear";
    public BiomeMultipliers maxMultipliers = new BiomeMultipliers();

    public static DepthScalingConfig fromSection(ConfigurationSection section) {
        DepthScalingConfig c = new DepthScalingConfig();
        if (section == null) return c;
        c.enabled = section.getBoolean("enabled", c.enabled);
        c.yThreshold = section.getInt("y-threshold", c.yThreshold);
        c.maxDepth = section.getInt("max-depth", c.maxDepth);
        c.scalingMode = section.getString("scaling-mode", c.scalingMode);
        c.maxMultipliers = BiomeMultipliers.fromSection(section.getConfigurationSection("max-multipliers"));
        return c;
    }
}