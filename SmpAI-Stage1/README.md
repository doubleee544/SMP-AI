# SMP AI — Stage 1

Minecraft Java **1.21.11** · **Minecraft Forge 61.2.4** · **Java 21**

Stage 1 is the foundation: a registered humanoid fake-player-style mob, basic hostile combat AI, spawn/count/remove commands, a placeholder skin texture, and a configurable global cap (1–1000).

## Commands

All commands require permission level 2 (OP):

- `/fakeplayer spawn <name>` — spawn one named AI at your position.
- `/fakeplayer spawnrandom <amount>` — spawn up to the requested number around you.
- `/fakeplayer remove <name>` — remove loaded fake players with that name.
- `/fakeplayer count` — show the loaded fake-player count and configured cap.
- `/fakeplayer removeall` — remove all loaded fake-player entities in every server dimension.

The AI attacks survival/adventure players it targets. It does not target creative or spectator players, and Stage 1 fake players do not target one another.

## Configuration

After the first launch, edit `config/smpai-common.toml`:

```toml
[fake_players]
maxFakePlayers = 1000
```

The value is clamped to 1–1000. This Stage 1 cap counts currently loaded fake-player entities across server dimensions. Persistent off-screen simulation and counting unloaded civilization members are planned for a later stage.

## Build

1. Install a Java 21 JDK.
2. Install Gradle 9.5 or newer, or open this folder in an IDE with Gradle support and let it use the project's toolchain.
3. Run `gradle build` from this folder.
4. The mod jar will be in `build/libs/`.

For development, run `gradle runClient` or `gradle runServer`.

## Stage 1 limitations

- The entity uses a placeholder humanoid texture; official username-to-Mojang-skin lookup and per-AI skin caching are not included yet.
- This is a hostile mob with a player-like silhouette, not a true `ServerPlayer` profile. That avoids creating fake authenticated accounts and gives us a stable base for custom combat AI.
- Loadout presets, shield/combat techniques, persistence, civilization simulation, Essential integration, and off-screen simulation are later stages.
