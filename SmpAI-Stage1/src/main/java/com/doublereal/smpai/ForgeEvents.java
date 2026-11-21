package com.doublereal.smpai;

import com.doublereal.smpai.command.FakePlayerCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SmpAI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEvents {
    private ForgeEvents() {}

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        FakePlayerCommands.register(event.getDispatcher());
    }
}
