package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class mobButton extends GuiButton {
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(ShadowOfMobdor.MODID, "textures/gui/mob_buttons.png");

    EntitySoMZombie mob;
    int mobTier;

    public mobButton(EntitySoMZombie mob, int buttonId, int x, int y) {
        super(buttonId, x, y, 22, 22, mob.getName() + (mob.isKilled() ? " (Dead)" : ""));
        this.mob = mob;
        this.mobTier = mob.getTier().tierNum();
    }

    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, mobTier * 22, i * 22, this.width, this.height);
            if (mob.isKilled())
                this.drawTexturedModalRect(this.x, this.y, (i * 22) - 22, 0, this.width, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
}
