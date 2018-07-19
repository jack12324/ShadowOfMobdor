package com.jack12324.som.capabilities.experience;

import net.minecraft.entity.player.EntityPlayer;

public class Experience implements IExperience {

    private EntityPlayer player;
    private int experience;
    private int level;

    public Experience(EntityPlayer player) {
        this.player = player;
        experience = 0;
        this.level = 1;
    }

    @Override
    public void addExperience(int amount) {
        experience += amount;
    }

    @Override
    public void setExperience(int amount) {
        experience = amount;
    }

    @Override
    public void clearExperience() {
        experience = 0;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getExperience() {
        return experience;
    }
}
