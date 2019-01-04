package com.jack12324.som.capabilities.player_stats;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public interface IPlayerStats {

    void addExperience(int amount);

    void setExperience(int amount);

    void clearExperience();

    void setLevel(int level);

    int getLevel();

    BlockPos getStartPosition();

    void setStartPosition(BlockPos position);

    int getExperience();

    EntityPlayer getPlayer();
}

