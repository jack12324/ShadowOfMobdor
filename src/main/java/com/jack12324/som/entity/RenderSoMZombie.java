package com.jack12324.som.entity;

import com.jack12324.som.ShadowOfMobdor;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderSoMZombie extends RenderLiving<EntitySoMZombie> {
    private static final ResourceLocation[] mobTexture = new ResourceLocation[6];
    double scale = 1.25;

    static {
        for (int i = 0; i <= 5; i++)
            mobTexture[i] = new ResourceLocation(ShadowOfMobdor.MODID, "textures/entities/zombie" + i + ".png");
    }

    public static final Factory FACTORY = new Factory();

    public RenderSoMZombie(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntitySoMZombie entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntitySoMZombie entity) {
        return mobTexture[entity.getTextureNumber()];
    }

    public static class Factory implements IRenderFactory<EntitySoMZombie> {

        @Override
        public Render<? super EntitySoMZombie> createRenderFor(RenderManager manager) {
            return new RenderSoMZombie(manager);
        }

    }
}
