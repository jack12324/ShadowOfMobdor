package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.gen.Invulnerabilities;
import com.jack12324.som.gen.Weaknesses;
import com.jack12324.som.network.SoMPacketHandler;
import com.jack12324.som.network.guispawn.GUISpawnPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

public class GuiDESC extends GuiScreen {
    EntitySoMZombie mob;        //mob from which to display information
    EntityPlayer player;        //player gui opened on, used to return to gui
    int mobIndex;
    int xSize = 256;            // width of gui
    int ySize = 166;            // height of gui
    float oldMouseX;            //last x position of mouse TODO do i need this
    float oldMouseY;            //lasy y position of mouse TODO do i need this
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID,
            "textures/gui/desc.png");

    public GuiDESC(int mobIndex, EntityPlayer player) {
        this.mob = player.getCapability(CapabilityHandler.NEM, null).getMob(mobIndex);
        this.mobIndex = mobIndex;
        this.player = player;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {//TODO add localization for strings

        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        if (!mob.isKilled())
            drawEntityOnScreen(x + 32, y + 83, 30, (float) (x + 26) - this.oldMouseX,
                        (float) (y + 80 - 50) - this.oldMouseY, mob);

        String name = mob.getName();
        fontRenderer.drawString(name, x + ((xSize - fontRenderer.getStringWidth(name)) / 2), y + 5,
                        0x404040);

        int col1 = x + 62;       //attribute column 1
        int col2 = x + 139;       //attribute column 2
        int row = y + 15;         //row location of stat
        int rowSpacing = 15;    //distance to place between y start of rows
        List<String> hovering = new ArrayList<>();

        fontRenderer.drawString("Level: " + mob.getLevel(), col1, row, 0x404040);
        fontRenderer.drawString("Class: " + mob.getMobClass(), col2, row, 0x404040);

        row += rowSpacing;

        fontRenderer.drawString("Tier: " + mob.getTier(), col1, row, 0x404040);

        row += rowSpacing;

        //render all stats and hovering text if needed
        fontRenderer.drawString("Health: " + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() * 1000)) / 1000.0, col1, row, 0x404040);

        row += rowSpacing;

        fontRenderer.drawString("Damage: " + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1000)) / 1000.0, col1, row, 0x404040);
        fontRenderer.drawString("Movement Speed: " + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 1000)) / 1000.0, col2, row, 0x404040);

        row += rowSpacing;

        fontRenderer.drawString("Armor: " + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue() * 1000)) / 1000.0, col1, row, 0x404040);
        fontRenderer.drawString("Armor Toughness: " + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue() * 1000)) / 1000.0, col2, row, 0x404040);

        //render hovering text (last so that it comes above other stuff
        if (inBox(mouseX, mouseY, col1, row - 2 * rowSpacing, fontRenderer.getStringWidth("Health: " + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() * 1000)) / 1000.0), fontRenderer.FONT_HEIGHT)) {
            hovering.add("Default: 20");
            hovering.add("Min: 0");
            hovering.add("Max: 1024");
            drawHoveringText(hovering, mouseX, mouseY);
            hovering.clear();
        }
        if (inBox(mouseX, mouseY, col2, row - rowSpacing, fontRenderer.getStringWidth("Movement "
                + "Speed: " + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 1000)) / 1000.0), fontRenderer.FONT_HEIGHT)) {
            hovering.add("Defaults:");
            hovering.add("Player: .1");
            hovering.add("Zombie: .23");
            hovering.add("Bat: .7");
            drawHoveringText(hovering, mouseX, mouseY);
            hovering.clear();
        }
        if (inBox(mouseX, mouseY, col1, row - rowSpacing, fontRenderer.getStringWidth("Damage: "
                + ((int) (mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1000)) / 1000.0), fontRenderer.FONT_HEIGHT)) {
            hovering.add("Damage dealt by attacks, in half-hearts.");
            hovering.add("Default: 2");
            drawHoveringText(hovering, mouseX, mouseY);
            hovering.clear();
        }

        //list Invulnerabilities and Weaknesses
        fontRenderer.drawString("Invulnerabilities:", x + 15, y + 95, 0x404040);
        StringBuilder text = new StringBuilder();
        for (Invulnerabilities inv : mob.getMobInv())
            text.append(inv.key() + " ");
        fontRenderer.drawSplitString(text.toString(), x + 15, y + 110, width - 30, 0x404040);

        fontRenderer.drawString("Weaknesses:", x + 15, y + 125, 0x404040);
        text = new StringBuilder();
        for (Weaknesses wk : mob.getMobWk())
            text.append(wk.key() + " ");
        fontRenderer.drawSplitString(text.toString(), x + 15, y + 140, width - 30, 0x404040);


        super.drawScreen(mouseX, mouseY, partialTicks);

        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }

    /**
     * Tells if position is within a box
     *
     * @param x      x position to test
     * @param y      y position to test
     * @param xStart x start position of box
     * @param yStart y start position of box
     * @param width  width of box
     * @param height height of box
     * @return true if x, y is within box parameters
     */
    private boolean inBox(int x, int y, int xStart, int yStart, int width, int height) {
        return x >= xStart && x <= xStart + width
                && y >= yStart && y <= yStart + height;
    }



    @Override
    public void initGui() {
        int y = (height - ySize) / 2;
        int x = (width - xSize) / 2;

        buttonList.add(new GuiButton(1, x - 20, y, 20, 20, "X"));
        buttonList.add(new GuiButton(2, x - 20, y + 21, 20, 20, "Spawn"));
        if (!this.player.isCreative()) {
            buttonList.get(1).enabled = false;
            buttonList.get(1).visible = false;
        }
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1)
            ShadowOfMobdor.proxy.openGUI(0, player, -1);
        else if (button.id == 2) {
            SoMPacketHandler.NETWORK.sendToServer(new GUISpawnPacket(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), this.mobIndex));
        } else
            super.actionPerformed(button);

    }


}
