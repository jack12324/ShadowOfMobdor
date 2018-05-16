package com.jack12324.som.gui;

import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;

public class GuiDESC extends GuiScreen {
    EntitySoMZombie mob;

    public GuiDESC(Entity mob) {
        this.mob = (EntitySoMZombie) mob;
    }
}
