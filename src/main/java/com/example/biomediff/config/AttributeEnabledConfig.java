package com.example.biomediff.config;

import org.bukkit.configuration.ConfigurationSection;

public class AttributeEnabledConfig {
    public boolean maxHealth = true;
    public boolean armor = true;
    public boolean armorToughness = true;
    public boolean attackDamage = true;
    public boolean attackSpeed = false;
    public boolean attackKnockback = false;
    public boolean knockbackResistance = true;
    public boolean luck = false;

    public static AttributeEnabledConfig fromSection(ConfigurationSection section) {
        AttributeEnabledConfig c = new AttributeEnabledConfig();
        if (section == null) return c;
        c.maxHealth = section.getBoolean("max-health", c.maxHealth);
        c.armor = section.getBoolean("armor", c.armor);
        c.armorToughness = section.getBoolean("armor-toughness", c.armorToughness);
        c.attackDamage = section.getBoolean("attack-damage", c.attackDamage);
        c.attackSpeed = section.getBoolean("attack-speed", c.attackSpeed);
        c.attackKnockback = section.getBoolean("attack-knockback", c.attackKnockback);
        c.knockbackResistance = section.getBoolean("knockback-resistance", c.knockbackResistance);
        c.luck = section.getBoolean("luck", c.luck);
        return c;
    }
}