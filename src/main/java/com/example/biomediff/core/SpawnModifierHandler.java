package com.example.biomediff.core;

import com.example.biomediff.BiomeDifficulty;
import com.example.biomediff.config.BiomeConfig;
import com.example.biomediff.config.BiomeMultipliers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class SpawnModifierHandler {
    private static final String MODIFIER_APPLIED_TAG = "biomediff_applied";

    public static void onEntitySpawn(LivingEntity entity) {
        if (!BiomeDifficulty.getConfig().enabled) return;
        if (hasModifierApplied(entity)) return;

        BiomeConfig config = BiomeDifficulty.getConfig();

        // Check if we should apply to this entity type
        if (!shouldApplyToEntity(entity, config)) return;

        // Determine if we should apply spawn-time modifiers to this entity
        boolean shouldApply = false;
        if (entity instanceof Player && config.playerMode == com.example.biomediff.config.ModifierMode.SPAWN_ONLY) {
            shouldApply = true;
        } else if (!(entity instanceof Player) && config.mobMode == com.example.biomediff.config.ModifierMode.SPAWN_ONLY) {
            shouldApply = true;
        }

        if (!shouldApply) return;

        String biomeId = AttributeManager.getBiomeId(entity);
        BiomeMultipliers multipliers = config.getMultipliersForBiome(biomeId);

        AttributeManager.applyModifiers(entity, biomeId, multipliers);
        setModifierApplied(entity);

        if (config.debugEnabled && config.debugLogBiomeChanges) {
            BiomeDifficulty.getLogger().info("Entity {} spawned in biome {}, applied spawn-time modifiers",
                entity.getName(), biomeId);
        }
    }

    public static boolean shouldApplyToEntity(LivingEntity entity, BiomeConfig config) {
        // Players always affected (luck only)
        if (entity instanceof Player) return true;

        // Mobs: check configuration
        if (entity instanceof Monster) return config.applyToHostileMobs;
        if (entity instanceof org.bukkit.entity.Animals
            || entity instanceof org.bukkit.entity.WaterMob
            || entity instanceof org.bukkit.entity.Fish
            || entity instanceof org.bukkit.entity.Ambient) return config.applyToPassiveMobs;

        // Neutral mobs (everything else that is a living entity)
        if (entity instanceof org.bukkit.entity.Mob) return config.applyToNeutralMobs;

        return false;
    }

    private static boolean hasModifierApplied(LivingEntity entity) {
        return entity.getScoreboardTags().contains(MODIFIER_APPLIED_TAG);
    }

    private static void setModifierApplied(LivingEntity entity) {
        entity.addScoreboardTag(MODIFIER_APPLIED_TAG);
    }

    public static void removeModifierApplied(LivingEntity entity) {
        entity.removeScoreboardTag(MODIFIER_APPLIED_TAG);
    }
}