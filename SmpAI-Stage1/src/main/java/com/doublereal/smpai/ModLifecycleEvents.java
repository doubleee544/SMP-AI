package com.doublereal.smpai;

import com.doublereal.smpai.entity.FakePlayerEntity;
import com.doublereal.smpai.registry.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SmpAI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModLifecycleEvents {
    private ModLifecycleEvents() {}

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FAKE_PLAYER.get(), FakePlayerEntity.createAttributes().build());
    }
}
