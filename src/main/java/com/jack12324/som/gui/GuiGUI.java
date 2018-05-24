package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.blocks.SoMBlocks;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.gen.MobTracker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiGUI extends GuiScreen {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID, "textures/gui/gui.png");
    int xSize = 176;
    int ySize = 166;
    EntityPlayer player;
    EntitySoMZombie[] mobs;

    public GuiGUI(EntityPlayer player) {
        super();
        this.player = player;
        int index = MobTracker.players.indexOf(player.getUniqueID());
        mobs = MobTracker.mobs.get(index);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        String name = I18n.format(SoMBlocks.gui.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
    }

    @Override
    public void initGui() {
        int index = MobTracker.players.indexOf(player.getUniqueID());
        EntitySoMZombie[] mobs = MobTracker.mobs.get(index);
        for (int i = 0; i < mobs.length; i++)
            buttonList.add(new GuiButton(i, 10 * i, 10 * 1, 10, 10, mobs[i].getName()));
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id < mobs.length && button.id >= 0)
            player.openGui(ShadowOfMobdor.instance, SoMGuiHandler.DESC, player.world, mobs[button.id].getEntityId(), 0, 0);
        else
            super.actionPerformed(button);

    }
}
