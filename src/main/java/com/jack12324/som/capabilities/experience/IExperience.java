package com.jack12324.som.capabilities.experience;

import net.minecraft.entity.player.EntityPlayer;

public interface IExperience {

    void addExperience(int amount);

    void setExperience(int amount);

    void clearExperience();

    void setLevel(int level);

    int getLevel();

    int getExperience();

    EntityPlayer getPlayer();
}

