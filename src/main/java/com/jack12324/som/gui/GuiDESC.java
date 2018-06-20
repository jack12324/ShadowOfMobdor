package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

public class GuiDESC extends GuiScreen {
    EntitySoMZombie mob;
    EntityPlayer player;
    int xSize = 176;
    int ySize = 166;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID, "textures/gui/desc.png");

    public GuiDESC(Entity mob, EntityPlayer player) {
        this.mob = (EntitySoMZombie) mob;
        this.player = player;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        String name = mob.getName();
        fontRenderer.drawString(name, (xSize - fontRenderer.getStringWidth(name)) / 2, y + 5, 0x404040);
        
        drawEntityOnScreen(x, y, 1, mouseX, mouseY, mob);

    }

    @Override
    public void initGui() {
        int y = (height - ySize) / 2;
        int x = (width - xSize) / 2;

        buttonList.add(new GuiButton(1, x, y, null));
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1)
            ShadowOfMobdor.proxy.openGUI(0, player, null);
        else
            super.actionPerformed(button);

    }
}
