package com.jack12324.som.gui;

import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

public class GuiDESC extends GuiScreen {
    EntitySoMZombie mob;

    public GuiDESC(Entity mob) {
        this.mob = (EntitySoMZombie) mob;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawEntityOnScreen();

    }
}
