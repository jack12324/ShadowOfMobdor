package com.jack12324.som.gui;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

public class GuiDESC extends GuiScreen {
    EntitySoMZombie mob;
    EntityPlayer player;
    int xSize = 176;
    int ySize = 166;
    float oldMouseX;
    float oldMouseY;
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ShadowOfMobdor.MODID, "textures/gui/desc.png");

    public GuiDESC(Entity mob, EntityPlayer player) {
        this.mob = (EntitySoMZombie) mob;
        this.player = player;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {//TODO add localization for strings

        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawEntityOnScreen(x + 26, y + 80, 30, (float) (x + 26) - this.oldMouseX,
                        (float) (y + 80 - 50) - this.oldMouseY, mob);

        String name = mob.getName();
        fontRenderer.drawString(name, x + ((xSize - fontRenderer.getStringWidth(name)) / 2), y + 5,
                        0x404040);

        int col1 = x + 40;       //attribute column 1
        int col2 = x + 90;       //attribute column 2
        int row = y + 15;         //row location of stat
        int rowSpacing = 15;    //distance to place between y start of rows

        fontRenderer.drawString("Level: " + mob.getLevel(), col1, row, 0x404040);
        fontRenderer.drawString("Class: " + mob.getMobClass(), col2, row, 0x404040);

        row += rowSpacing;

        fontRenderer.drawString("Tier: " + mob.getTier(), col1, row, 0x404040);

        row += rowSpacing;

        fontRenderer.drawString("Health: " + mob.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue(), col1, row, 0x404040);

        row += rowSpacing;

        fontRenderer.drawString("Damage: " + mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(), col1, row, 0x404040);
        fontRenderer.drawString("Movement Speed: " + mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(), col2, row, 0x404040);

        row += rowSpacing;

        fontRenderer.drawString("Armor: " + mob.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue(), col1, row, 0x404040);
        fontRenderer.drawString("Armor Toughness: " + mob.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue(), col2, row, 0x404040);

        fontRenderer.drawString("Invulnerabilities:", x + 15, y + 100, 0x404040);
        fontRenderer.drawString("Weaknesses:", x + 15, y + 125, 0x404040);



        super.drawScreen(mouseX, mouseY, partialTicks);

        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }

    @Override
    public void initGui() {
        int y = (height - ySize) / 2;
        int x = (width - xSize) / 2;

        buttonList.add(new GuiButton(1, x - 20, y, 20, 20, "Back"));
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1)
            ShadowOfMobdor.proxy.openGUI(0, player, null);
        else
            super.actionPerformed(button);

    }


}
