package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.blocks.SoMBlocks;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static com.jack12324.som.SoMConst.B_START_SPAWN;
import static com.jack12324.som.SoMConst.GUI_WARN;

public class GuiStart extends GuiScreen {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID,
            "textures/gui/gui.png");
    int xSize = 221;
    int ySize = 166;
    EntityPlayer player;

    public GuiStart(EntityPlayer player) {
        super();
        this.player = player;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        String welcome = "Welcome to Shadow of Mobdor"; //todo localize
        String name = I18n.format(SoMBlocks.gui.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, x + (xSize / 2 - fontRenderer.getStringWidth(name) / 2),
                y + 6, 0x404040);
        fontRenderer.drawString(welcome, x + (xSize / 2 - fontRenderer.getStringWidth(welcome) / 2),
                y + 12, 0x404040);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        buttonList.clear();
        int x = (width - xSize) / 2;

        buttonList.add(new GuiButton(B_START_SPAWN, x - 20, height - ySize, "Set Spawn"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == B_START_SPAWN)
            ShadowOfMobdor.proxy.openGUI(GUI_WARN, player, button.id);
        else
            super.actionPerformed(button);

    }

}
