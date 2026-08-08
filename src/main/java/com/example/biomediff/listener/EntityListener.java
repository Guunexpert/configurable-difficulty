package com.example.biomediff.listener;

import com.example.biomediff.core.DynamicModifierHandler;
import com.example.biomediff.core.SpawnModifierHandler;
import com.example.biomediff.core.XpHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity == null) return;
        SpawnModifierHandler.onEntitySpawn(entity);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGenericSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        SpawnModifierHandler.onEntitySpawn(livingEntity);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        SpawnModifierHandler.onEntitySpawn(event.getPlayer());
        DynamicModifierHandler.dynamicCheck(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        DynamicModifierHandler.onEntityRemoved(entity);

        // Scale experience drop
        int baseXp = event.getDroppedExp();
        int scaled = XpHandler.scaleExperience(baseXp, entity);
        if (scaled != baseXp) {
            event.setDroppedExp(scaled);
        }
    }
}