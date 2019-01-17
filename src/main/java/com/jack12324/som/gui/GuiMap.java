package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static com.jack12324.som.SoMConst.GUI_MAIN;

public class GuiMap extends GuiScreen {
    private final int BACK = 987132;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID,
            "textures/gui/gui.png");
    int xSize = 256;
    int ySize = 256;
    EntityPlayer player;

    public GuiMap(EntityPlayer player) {
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

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        buttonList.clear();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        buttonList.add(new GuiButton(BACK, x - 20, y, 20, 20, "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == BACK)
            ShadowOfMobdor.proxy.openGUI(GUI_MAIN, player, -1); //todo add directory gui or tabs?
        else
            super.actionPerformed(button);

    }
}
