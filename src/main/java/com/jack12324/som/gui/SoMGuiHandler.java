package com.jack12324.som.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class SoMGuiHandler implements IGuiHandler {
    public static final int GUI = 0;
    public static final int DESC = 1;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI:
                return new GuiGUI(player);
            case DESC:
                return new GuiDESC(world.getEntityByID(x));
            default:
                return null;
        }
    }
}
