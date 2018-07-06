package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.SoMConst;
import com.jack12324.som.blocks.SoMBlocks;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.gen.Leveling;
import com.jack12324.som.gen.MobTracker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.HoverChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiGUI extends GuiScreen {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID,
            "textures/gui/gui.png");
    int xSize = 221;
    int ySize = 166;
    EntityPlayer player;
    EntitySoMZombie[] mobs;
    int index;

    public GuiGUI(EntityPlayer player) {
        super();
        this.player = player;
        index = MobTracker.players.indexOf(player.getUniqueID());
        mobs = player.getCapability(CapabilityHandler.NEM, null).getMobs();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        //draw XP bar
        int xBar = x + 8;
        int yBar = y + 8;
        int ExToNext = SoMConst.levels[MobTracker.playerLevels.get(index)];
        double barPer = (Leveling.playerXP.get(index) / (double) ExToNext);
        drawTexturedModalRect(xBar, (int) (yBar + (150 - (barPer * 150))), 221, (int) (150 - (barPer * 150)), 20, (int) (barPer * 150));
        drawTexturedModalRect(xBar, yBar, 241, 0, 15, 150);
        HoverChecker barHover = new HoverChecker(yBar, yBar + 150, xBar, xBar + 20, 0);
        List<String> hovering = new ArrayList<>();

        String name = I18n.format(SoMBlocks.gui.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, x + (xSize / 2 - fontRenderer.getStringWidth(name) / 2),
                        y + 6, 0x404040);



        super.drawScreen(mouseX, mouseY, partialTicks);

        //hovering text for buttons
        for (GuiButton button : buttonList) {
            if (button instanceof mobButton) {
                if (button.isMouseOver())
                    drawHoveringText(button.displayString, mouseX, mouseY);
            }
        }

        //hovering text for xp bar
        if (barHover.checkHover(mouseX, mouseY)) {
            hovering.clear();
            hovering.add("Player Level: " + MobTracker.playerLevels.get(index));               //TODO localization
            hovering.add("Progress: " + (int) (barPer * 100) + "%");
            hovering.add("XP: " + Leveling.playerXP.get(index) + "/" + ExToNext);
            drawHoveringText(hovering, mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        buttonList.clear();
        int butX;
        int butWidth;
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        //adds button for each mob
        for (int i = 0; i < mobs.length; i++) {
            butWidth = calcButtonWidth() + 4;         //calculate
            // needed width to fit name
            butX = ((xSize - butWidth) / 2) + x;                                        //center
            // button
            buttonList.add(new mobButton(mobs[i], i, butX, y + 15 + (22 * i)));
        }
    }

    private int calcButtonWidth() {
        int max = 0;
        for (EntitySoMZombie mob : mobs) {
            if (fontRenderer.getStringWidth(mob.getName()) > max)
                max = fontRenderer.getStringWidth(mob.getName());
        }
        return max;
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id < mobs.length && button.id >= 0)
            ShadowOfMobdor.proxy.openGUI(1, player, mobs[button.id]);
        else
            super.actionPerformed(button);

    }
}
