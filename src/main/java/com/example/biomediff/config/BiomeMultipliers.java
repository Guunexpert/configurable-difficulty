package com.example.biomediff.config;

import org.bukkit.configuration.ConfigurationSection;

public class BiomeMultipliers {
    public double maxHealth = 1.0;
    public double armor = 1.0;
    public double armorToughness = 1.0;
    public double attackDamage = 1.0;
    public double attackSpeed = 1.0;
    public double attackKnockback = 1.0;
    public double knockbackResistance = 0.0;
    public double luck = 1.0;
    public double xp = 1.0;

    public static BiomeMultipliers combine(BiomeMultipliers a, BiomeMultipliers b) {
        BiomeMultipliers result = new BiomeMultipliers();
        result.maxHealth = a.maxHealth * b.maxHealth;
        result.armor = a.armor * b.armor;
        result.armorToughness = a.armorToughness * b.armorToughness;
        result.attackDamage = a.attackDamage * b.attackDamage;
        result.attackSpeed = a.attackSpeed * b.attackSpeed;
        result.attackKnockback = a.attackKnockback * b.attackKnockback;
        result.knockbackResistance = a.knockbackResistance + b.knockbackResistance;
        result.luck = a.luck * b.luck;
        result.xp = a.xp * b.xp;
        return result;
    }

    public static BiomeMultipliers fromSection(ConfigurationSection section) {
        BiomeMultipliers m = new BiomeMultipliers();
        if (section == null) return m;
        m.maxHealth = section.getDouble("max-health", m.maxHealth);
        m.armor = section.getDouble("armor", m.armor);
        m.armorToughness = section.getDouble("armor-toughness", m.armorToughness);
        m.attackDamage = section.getDouble("attack-damage", m.attackDamage);
        m.attackSpeed = section.getDouble("attack-speed", m.attackSpeed);
        m.attackKnockback = section.getDouble("attack-knockback", m.attackKnockback);
        m.knockbackResistance = section.getDouble("knockback-resistance", m.knockbackResistance);
        m.luck = section.getDouble("luck", m.luck);
        m.xp = section.getDouble("xp", m.xp);
        return m;
    }
}