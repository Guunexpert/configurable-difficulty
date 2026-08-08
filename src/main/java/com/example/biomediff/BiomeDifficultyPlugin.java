package com.example.biomediff;

import com.example.biomediff.core.DynamicModifierHandler;
import com.example.biomediff.listener.EntityListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BiomeDifficultyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        BiomeDifficulty.init(this);

        getServer().getPluginManager().registerEvents(new EntityListener(), this);

        DynamicModifierHandler.startScheduler();

        getLogger().info("Configurable Difficulty plugin enabled");
    }

    @Override
    public void onDisable() {
        DynamicModifierHandler.stopScheduler();
        getLogger().info("Configurable Difficulty plugin disabled");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!"biomediff".equalsIgnoreCase(command.getName())) return false;

        if (args.length == 0) {
            sender.sendMessage("§aBiome Diff §7- §fuse /biomediff reload");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                if (!sender.hasPermission("biomediff.admin")) {
                    sender.sendMessage("§cYou don't have permission to do that");
                    return true;
                }
                reloadConfig();
                BiomeDifficulty.loadConfig();
                DynamicModifierHandler.startScheduler();
                DynamicModifierHandler.reapplyAll();
                sender.sendMessage("§aConfig reloaded successfully");
            }
            case "info" -> {
                sender.sendMessage("§7Enabled: §f" + BiomeDifficulty.getConfig().enabled);
                sender.sendMessage("§7playerMode: §f" + BiomeDifficulty.getConfig().playerMode);
                sender.sendMessage("§7mobMode: §f" + BiomeDifficulty.getConfig().mobMode);
                sender.sendMessage("§7checkInterval: §f" + BiomeDifficulty.getConfig().checkInterval);
                sender.sendMessage("§7xpEnabled: §f" + BiomeDifficulty.getConfig().xpEnabled);
            }
            default -> sender.sendMessage("§cUsage: /biomediff <reload|info>");
        }
        return true;
    }
}