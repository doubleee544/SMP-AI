package com.doublereal.smpai;

import com.doublereal.smpai.registry.ModEntities;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

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
        var modBus = Mod.EventBusSubscriber.Bus.MOD.bus().get();
        ModEntities.ENTITY_TYPES.register(modBus);
        context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }
}
