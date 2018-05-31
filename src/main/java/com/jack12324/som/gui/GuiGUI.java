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
    int x = (width - xSize) / 2;
    int y = (height - ySize) / 2;
    EntityPlayer player;
    EntitySoMZombie[] mobs;
    int index;

    public GuiGUI(EntityPlayer player) {
        super();
        this.player = player;
        index = MobTracker.players.indexOf(player.getUniqueID());
        mobs = MobTracker.mobs.get(index);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        String name = I18n.format(SoMBlocks.gui.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
    }

    @Override
    public void initGui() {
        int butX;
        int butWidth;

        //adds button for each mob
        for (int i = 0; i < mobs.length; i++) {
            butWidth = fontRenderer.getStringWidth(mobs[i].getName());         //calculate needed width to fit name
            butX = (xSize - butWidth) / 2;                                        //center button
            buttonList.add(new GuiButton(i, butX, y + 10 + (11 * i), butWidth, 10, mobs[i].getName()));
        }
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id < mobs.length && button.id >= 0)
            ShadowOfMobdor.proxy.openGUI(1, player, mobs[button.id]);
        else
            super.actionPerformed(button);

    }
}
