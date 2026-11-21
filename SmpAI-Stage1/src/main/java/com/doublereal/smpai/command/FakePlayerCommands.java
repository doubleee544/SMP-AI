package com.doublereal.smpai.command;

import com.doublereal.smpai.SmpAI;
import net.minecraft.world.entity.EntitySpawnReason;
import com.doublereal.smpai.entity.FakePlayerEntity;
import com.doublereal.smpai.registry.ModEntities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.entity.player.Player;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public final class FakePlayerCommands {
    private static final String[] RANDOM_NAMES = {
            "PvPLegend82", "BlockRunner", "EnderFighter", "CraftedChaos",
            "NotYourRival", "DiamondRush", "VoidWalker", "SwordMain",
            "TotemEnjoyer", "BridgeBuilder", "NetherNomad", "PixelRaider"
    };

    private FakePlayerCommands() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fakeplayer")
                .requires(Commands.hasPermission(Commands.LEVEL_ADMINS))
                .then(Commands.literal("spawn")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(context -> spawn(context.getSource(), StringArgumentType.getString(context, "name")))))
                .then(Commands.literal("spawnrandom")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 1000))
                                .executes(context -> spawnRandom(context.getSource(), IntegerArgumentType.getInteger(context, "amount")))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(context -> removeNamed(context.getSource(), StringArgumentType.getString(context, "name")))))
                .then(Commands.literal("count")
                        .executes(context -> count(context.getSource())))
                .then(Commands.literal("removeall")
                        .executes(context -> removeAll(context.getSource())))
        );
    }

    private static int spawn(CommandSourceStack source, String name) {
        MinecraftServer server = source.getServer();
        if (getCount(server) >= SmpAI.MAX_FAKE_PLAYERS.get()) {
            source.sendFailure(Component.literal("Fake player limit reached (" + SmpAI.MAX_FAKE_PLAYERS.get() + ")."));
            return 0;
        }
        ServerLevel level = source.getLevel();
        FakePlayerEntity fake = ModEntities.FAKE_PLAYER.get().create(level, EntitySpawnReason.COMMAND);
        if (fake == null) {
            source.sendFailure(Component.literal("Could not create fake player entity."));
            return 0;
        }
        fake.setPos(source.getPosition().x, source.getPosition().y, source.getPosition().z);
        fake.setYRot(source.getRotation().y);
        fake.setXRot(0.0F);
        fake.setCustomName(Component.literal(name));
        fake.setCustomNameVisible(true);
        level.addFreshEntity(fake);
        source.sendSuccess(() -> Component.literal("Spawned fake player " + name + "."), true);
        return 1;
    }

    private static int spawnRandom(CommandSourceStack source, int amount) {
        MinecraftServer server = source.getServer();
        int room = SmpAI.MAX_FAKE_PLAYERS.get() - getCount(server);
        int toSpawn = Math.min(amount, room);
        if (toSpawn <= 0) {
            source.sendFailure(Component.literal("Fake player limit reached (" + SmpAI.MAX_FAKE_PLAYERS.get() + ")."));
            return 0;
        }

        ServerLevel level = source.getLevel();
        int spawned = 0;
        for (int i = 0; i < toSpawn; i++) {
            FakePlayerEntity fake = ModEntities.FAKE_PLAYER.get().create(level, EntitySpawnReason.COMMAND);
            if (fake == null) continue;
            double angle = (Math.PI * 2.0D * i) / Math.max(1, toSpawn);
            double x = source.getPosition().x + Math.cos(angle) * 2.5D;
            double z = source.getPosition().z + Math.sin(angle) * 2.5D;
            fake.setPos(x, source.getPosition().y, z);
            fake.setYRot((float) (angle * 180.0D / Math.PI));
            fake.setXRot(0.0F);
            fake.setCustomName(Component.literal(randomName()));
            fake.setCustomNameVisible(true);
            if (level.addFreshEntity(fake)) spawned++;
        }
        int finalSpawned = spawned;
        source.sendSuccess(() -> Component.literal("Spawned " + finalSpawned + " fake player(s)."), true);
        if (spawned < amount) {
            source.sendFailure(Component.literal("The server cap limited this batch. Current cap: " + SmpAI.MAX_FAKE_PLAYERS.get() + "."));
        }
        return spawned;
    }

    private static int removeNamed(CommandSourceStack source, String name) {
        int removed = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (FakePlayerEntity fake : level.getEntities(EntityTypeTest.forClass(FakePlayerEntity.class), Entity::isAlive)) {
                if (fake.getName().getString().equalsIgnoreCase(name)) {
                    fake.discard();
                    removed++;
                }
            }
        }
        int finalRemoved = removed;
        if (removed == 0) {
            source.sendFailure(Component.literal("No loaded fake player named " + name + " was found."));
            return 0;
        }
        source.sendSuccess(() -> Component.literal("Removed " + finalRemoved + " fake player(s) named " + name + "."), true);
        return removed;
    }

    private static int count(CommandSourceStack source) {
        int count = getCount(source.getServer());
        source.sendSuccess(() -> Component.literal("Loaded fake players: " + count + " / " + SmpAI.MAX_FAKE_PLAYERS.get()), false);
        return count;
    }

    private static int removeAll(CommandSourceStack source) {
        int removed = 0;
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (FakePlayerEntity fake : level.getEntities(EntityTypeTest.forClass(FakePlayerEntity.class), Entity::isAlive)) {
                fake.discard();
                removed++;
            }
        }
        int finalRemoved = removed;
        source.sendSuccess(() -> Component.literal("Removed " + finalRemoved + " fake player(s)."), true);
        return removed;
    }

    private static int getCount(MinecraftServer server) {
        int count = 0;
        for (ServerLevel level : server.getAllLevels()) {
            count += level.getEntities(EntityTypeTest.forClass(FakePlayerEntity.class), Entity::isAlive).size();
        }
        return count;
    }

    private static String randomName() {
        String base = RANDOM_NAMES[ThreadLocalRandom.current().nextInt(RANDOM_NAMES.length)];
        return base + ThreadLocalRandom.current().nextInt(10, 1000);
    }
}
