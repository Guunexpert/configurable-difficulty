# Configurable Difficulty (Plugin)

A server-side Minecraft **plugin** (Paper / Purpur / Spigot / Bukkit) for 1.21.11 that adjusts entity attributes based on their biome, dimension, and depth.

> This branch (`main-plugin`) is dedicated to the Bukkit-based plugin. For the Fabric/NeoForge mod, see the `main` branch.

## Features

- **Triple Multiplier System**:
  - **Dimension Multipliers**: Base difficulty per dimension (Nether, End, etc.)
  - **Biome Multipliers**: Difficulty scaling per biome
  - **Depth Scaling**: Progressive difficulty based on Y-coordinate (Overworld only)
  - **Final Formula**: `Dimension × Biome × Depth`

- **Hybrid Modifier System**:
  - Players: Always affected (luck attribute only, dynamic mode)
  - Mobs: Configurable whether to apply (combat attributes only, spawn-only mode)

- **Works with ALL biome/dimension plugins** (any `namespace:key`)

- **Auto-fallback**: Unconfigured dimensions use Overworld as baseline; unconfigured biomes use `default-multipliers`

- **Attribute Split by Entity Type**:
  - **Players**: Only receive **Luck** attribute (better loot quality)
  - **Mobs**: Receive all combat attributes (Health, Armor, Damage, etc.)

- **XP Multiplier**: Optional XP reward scaling per dimension/biome/depth

## Requirements

- Minecraft **1.21.11** server (Paper, Purpur, Spigot, Bukkit, or compatible fork)
- Java 21

## Installation

1. Download `configurable-difficulty-1.2.0.jar`
2. Place it in the server's `plugins/` folder
3. Start the server
4. Edit `plugins/configurable-difficulty/config.yml` (generated on first run)
5. Reload with `/biomediff reload`

## Commands

| Command | Description | Permission |
|---|---|---|
| `/biomediff reload` | Reload the config | `biomediff.admin` |
| `/biomediff info` | Show current config summary | — |

## Config Structure

All multipliers use: `1.0 = no change`, `2.0 = 2x harder`, `0.5 = half difficulty`. `knockback-resistance` uses an additive value.

```yaml
enabled: true
player-mode: DYNAMIC
mob-mode: SPAWN_ONLY
check-interval: 20

apply-to-hostile-mobs: true
apply-to-passive-mobs: false
apply-to-neutral-mobs: false
xp-enabled: false

enabled-attributes:
  max-health: true
  armor: true
  armor-toughness: true
  attack-damage: true
  attack-speed: false
  attack-knockback: false
  knockback-resistance: true
  luck: false

default-multipliers:
  max-health: 1.0
  armor: 1.0
  armor-toughness: 1.0
  attack-damage: 1.0
  attack-speed: 1.0
  attack-knockback: 1.0
  knockback-resistance: 0.0
  luck: 1.0
  xp: 1.0

dimension-multipliers:
  minecraft:the_nether:
    max-health: 1.5
    armor: 1.3
    attack-damage: 1.5
    luck: 1.2
    xp: 1.5
  minecraft:the_end:
    max-health: 2.0
    armor: 1.5
    attack-damage: 2.0
    luck: 1.5
    xp: 2.0

biome-multipliers:
  minecraft:desert:
    max-health: 1.5
    attack-damage: 1.3
    knockback-resistance: 0.1
    xp: 1.2
  minecraft:deep_dark:
    max-health: 2.5
    armor: 2.0
    attack-damage: 2.0
    xp: 2.0

depth-scaling:
  enabled: false
  y-threshold: 0
  max-depth: -64
  scaling-mode: linear
  max-multipliers:
    max-health: 1.0
    armor: 1.0
    attack-damage: 1.0
```

### Multiplier Calculation

```
Final Multiplier = Dimension Multiplier × Biome Multiplier × Depth Multiplier
```

Example — zombie in The End:
- Dimension: 2.0x health
- Biome: default 1.0x
- Final: **2.0x health** (zombie 20 HP → 40 HP)

## Building

```bash
./gradlew build
```

Output: `build/libs/configurable-difficulty-1.2.0.jar`

## License

MIT License