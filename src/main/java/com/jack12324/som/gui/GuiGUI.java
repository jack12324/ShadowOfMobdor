package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.SoMConst;
import com.jack12324.som.blocks.SoMBlocks;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.capabilities.player_stats.IPlayerStats;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.network.SoMPacketHandler;
import com.jack12324.som.network.player_stats.PSPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.HoverChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jack12324.som.SoMConst.GUI_DESC;

public class GuiGUI extends GuiScreen {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID,
            "textures/gui/gui.png");
    int xSize = 221;
    int ySize = 166;
    EntityPlayer player;
    EntitySoMZombie[] mobs;
    IPlayerStats xpCapability;

    public GuiGUI(EntityPlayer player) {
        super();
        this.player = player;
        mobs = player.getCapability(CapabilityHandler.NEM, null).getMobs();
        xpCapability = this.player.getCapability(CapabilityHandler.XP, null);
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
        int ExToNext = SoMConst.levels[xpCapability.getLevel()];
        double barPer = (xpCapability.getExperience() / (double) ExToNext);
        drawTexturedModalRect(xBar, (int) (yBar + (150 - (barPer * 150))), 221, (int) (150 - (barPer * 150)), 20, (int) (barPer * 150) + 1);
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
            hovering.add("Player Level: " + xpCapability.getLevel());               //TODO localization
            hovering.add("Progress: " + (int) (barPer * 100) + "%");
            hovering.add("XP: " + xpCapability.getExperience() + "/" + ExToNext);
            drawHoveringText(hovering, mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        buttonList.clear();
        int butWidth;
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        int butX = x + 30;

        //adds button for each mob
        for (int i = 0; i < mobs.length; i++) {
            butWidth = calcButtonWidth() + 4;
            buttonList.add(new mobButton(mobs[i], i, butX, i < mobs.length / 2 ? y + 30 : y + 54));
            if (i == mobs.length / 2 - 1)
                butX = x + 30;
            else
                butX += 23;
        }
        buttonList.add(new GuiButton(999, width - xSize - 20, height - ySize, "Set Spawn"));
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
            ShadowOfMobdor.proxy.openGUI(GUI_DESC, player, button.id);
        else if (button.id == 999) {
            IPlayerStats cap = player.getCapability(CapabilityHandler.XP, null);
            cap.setStartPosition(player.getPosition());
            SoMPacketHandler.NETWORK.sendTo(new PSPacket(cap.getExperience(), cap.getLevel(), cap.getStartPosition()), (EntityPlayerMP) player);
        } else
            super.actionPerformed(button);

    }
}
