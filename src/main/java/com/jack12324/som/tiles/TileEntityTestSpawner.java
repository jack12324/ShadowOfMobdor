package com.jack12324.som.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTestSpawner extends TileEntity {

    private int level;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("level", level);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        level = compound.getInteger("level");
        super.readFromNBT(compound);
    }

    public int getLevel() {
        return level;
    }

    public void increment() {
        level++;
        markDirty();
    }

    public void decrement() {
        if (level <= 1)
            level = 1;
        else
            level--;
        markDirty();
    }
}
