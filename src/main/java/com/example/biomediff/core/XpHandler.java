package com.example.biomediff.core;

import com.example.biomediff.BiomeDifficulty;
import org.bukkit.entity.LivingEntity;

public class XpHandler {
    public static int scaleExperience(int baseXp, LivingEntity entity) {
        if (baseXp <= 0) return baseXp;
        if (!BiomeDifficulty.getConfig().xpEnabled) return baseXp;

        double multiplier = AttributeManager.getXpMultiplier(entity);
        int scaled = (int) Math.round(baseXp * multiplier);
        return Math.max(scaled, 1);
    }
}