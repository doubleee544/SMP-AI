package com.doublereal.smpai;

import com.doublereal.smpai.command.FakePlayerCommands;
import com.doublereal.smpai.registry.ModEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import com.doublereal.smpai.entity.FakePlayerEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SmpAI.MOD_ID)
public class SmpAI {
    public static final String MOD_ID = "smpai";

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec.IntValue MAX_FAKE_PLAYERS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("fake_players");
        MAX_FAKE_PLAYERS = builder
                .comment("Maximum number of loaded fake player AI entities across the server. Range: 1-1000.")
                .defineInRange("maxFakePlayers", 1000, 1, 1000);
        builder.pop();
        COMMON_SPEC = builder.build();
    }

    public SmpAI(FMLJavaModLoadingContext context) {
        var modBus = context.getModEventBus();
        ModEntities.ENTITY_TYPES.register(modBus);
        modBus.addListener(this::onEntityAttributes);
        context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
    }

    private void onEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FAKE_PLAYER.get(), FakePlayerEntity.createAttributes().build());
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        FakePlayerCommands.register(event.getDispatcher());
    }
}
