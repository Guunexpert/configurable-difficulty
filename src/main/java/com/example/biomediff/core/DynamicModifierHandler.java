package com.example.biomediff.core;

import com.example.biomediff.BiomeDifficulty;
import com.example.biomediff.config.BiomeConfig;
import com.example.biomediff.config.BiomeMultipliers;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.WeakHashMap;

public class DynamicModifierHandler {
    private static final java.util.Map<UUID, String> entityBiomeMap = new WeakHashMap<>();
    private static BukkitTask schedulerTask;

    public static void startScheduler() {
        stopScheduler();
        int interval = Math.max(Math.min(BiomeDifficulty.getConfig().checkInterval, 1), 1);
        schedulerTask = Bukkit.getScheduler().runTaskTimer(BiomeDifficulty.getPlugin(), DynamicModifierHandler::onServerTick, 0L, interval);
    }

    public static void stopScheduler() {
        if (schedulerTask != null) {
            schedulerTask.cancel();
            schedulerTask = null;
        }
    }

    public static void clearState() {
        entityBiomeMap.clear();
    }

    public static void onServerTick() {
        if (!BiomeDifficulty.getConfig().enabled) return;

        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                dynamicCheck(entity);
            }
        }
    }

    public static void dynamicCheck(LivingEntity entity) {
        if (!BiomeDifficulty.getConfig().enabled) return;

        BiomeConfig config = BiomeDifficulty.getConfig();

        // Check if we should apply to this entity type
        if (!SpawnModifierHandler.shouldApplyToEntity(entity, config)) return;

        // Determine if we should use dynamic updates for this entity
        boolean shouldUseDynamic = false;
        if (entity instanceof Player && config.playerMode == com.example.biomediff.config.ModifierMode.DYNAMIC) {
            shouldUseDynamic = true;
        } else if (!(entity instanceof Player) && config.mobMode == com.example.biomediff.config.ModifierMode.DYNAMIC) {
            shouldUseDynamic = true;
        }

        if (!shouldUseDynamic) return;

        String currentBiomeId = AttributeManager.getBiomeId(entity);
        String previousBiomeId = entityBiomeMap.get(entity.getUniqueId());

        // If biome hasn't changed, do nothing
        if (currentBiomeId.equals(previousBiomeId)) return;

        // Remove old modifiers if there were any
        if (previousBiomeId != null) {
            AttributeManager.removeModifiers(entity);
        }

        // Apply new modifiers
        BiomeMultipliers multipliers = config.getMultipliersForBiome(currentBiomeId);
        AttributeManager.applyModifiers(entity, currentBiomeId, multipliers);

        // Update tracked biome
        entityBiomeMap.put(entity.getUniqueId(), currentBiomeId);

        if (config.debugEnabled && config.debugLogBiomeChanges) {
            BiomeDifficulty.getLogger().info("Entity {} moved from biome {} to {}, updated modifiers",
                entity.getName(), previousBiomeId != null ? previousBiomeId : "none", currentBiomeId);
        }
    }

    public static void onEntityRemoved(LivingEntity entity) {
        entityBiomeMap.remove(entity.getUniqueId());
    }

    public static void reapplyAll() {
        if (!BiomeDifficulty.getConfig().enabled) return;

        clearState();
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                SpawnModifierHandler.reapplyNow(entity);
            }
        }
    }
}