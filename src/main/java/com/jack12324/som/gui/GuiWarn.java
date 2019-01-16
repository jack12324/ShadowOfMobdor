package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.capabilities.nemesis.INemesisList;
import com.jack12324.som.capabilities.player_stats.IPlayerStats;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.network.SoMPacketHandler;
import com.jack12324.som.network.nemesis.NemListPacket;
import com.jack12324.som.network.player_stats.PSPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static com.jack12324.som.SoMConst.*;

public class GuiWarn extends GuiScreen {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID,
            "textures/gui/gui.png");
    int xSize = 221;
    int ySize = 166;
    EntityPlayer player;

    public GuiWarn(EntityPlayer player) {
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
        String warnText = "This will generate nemesis mobs centered on this location\n " +
                "The spawn location of these mobs will not be able to be changed.\n " +
                "Note: you can change the spawn location of future mobs"; //todo localize
        fontRenderer.drawString(warnText, x + (xSize / 2 - fontRenderer.getStringWidth(warnText) / 2),
                y + 18, 0x404040);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        buttonList.clear();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        buttonList.add(new GuiButton(B_WARN_CONFIRM, x - 20, y, "Confirm"));
        buttonList.add(new GuiButton(B_WARN_BACK, x + 20, y, "Cancel"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == B_WARN_CONFIRM) {
            INemesisList nemCap = player.getCapability(CapabilityHandler.NEM, null);
            if (nemCap.isEmpty()) {
                for (int i = 0; i < 10; i++)
                    nemCap.addMob(new EntitySoMZombie(player), i);
            }
            SoMPacketHandler.NETWORK.sendTo(new NemListPacket(nemCap.getMobs()), (EntityPlayerMP) player);
            IPlayerStats statCap = player.getCapability(CapabilityHandler.XP, null);
            statCap.setStartPosition(player.getPosition());
            SoMPacketHandler.NETWORK.sendTo(new PSPacket(statCap.getExperience(), statCap.getLevel(), statCap.getStartPosition()), (EntityPlayerMP) player);
        }
        else if (button.id == B_WARN_BACK)
            ShadowOfMobdor.proxy.openGUI(GUI_START, player, -1);
        else
            super.actionPerformed(button);

    }
}
