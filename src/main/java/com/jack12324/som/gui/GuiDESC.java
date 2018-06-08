package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

public class GuiDESC extends GuiScreen {
    EntitySoMZombie mob;
    int xSize = 176;
    int ySize = 166;
    float oldMouseX;
    float oldMouseY;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID, "textures/gui/desc.png");

    public GuiDESC(Entity mob) {
        this.mob = (EntitySoMZombie) mob;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawEntityOnScreen(x + 25, y + 80, 30, (float) (x + 25) - this.oldMouseX,
                        (float) (y + 80 - 50) - this.oldMouseY, mob);

        String name = mob.getName();
        fontRenderer.drawString(name, x + ((xSize - fontRenderer.getStringWidth(name)) / 2), y + 5,
                        0x404040);



        super.drawScreen(mouseX, mouseY, partialTicks);

        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }


}
