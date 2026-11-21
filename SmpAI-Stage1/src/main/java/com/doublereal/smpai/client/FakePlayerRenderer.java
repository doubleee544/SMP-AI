package com.doublereal.smpai.client;

import com.doublereal.smpai.SmpAI;
import com.doublereal.smpai.entity.FakePlayerEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class FakePlayerRenderer extends HumanoidMobRenderer<FakePlayerEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(SmpAI.MOD_ID, "fake_player"), "main");
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(SmpAI.MOD_ID, "textures/entity/fake_player.png");

    public FakePlayerRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(LAYER)), 0.5F);
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F), 64, 64);
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return TEXTURE;
    }
}
