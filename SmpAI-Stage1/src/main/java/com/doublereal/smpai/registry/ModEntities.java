package com.doublereal.smpai.registry;

import com.doublereal.smpai.SmpAI;
import com.doublereal.smpai.entity.FakePlayerEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, SmpAI.MOD_ID);

    public static final RegistryObject<EntityType<FakePlayerEntity>> FAKE_PLAYER = ENTITY_TYPES.register(
            "fake_player",
            () -> EntityType.Builder.of(FakePlayerEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(SmpAI.MOD_ID, "fake_player")))
    );

    private ModEntities() {}
}
