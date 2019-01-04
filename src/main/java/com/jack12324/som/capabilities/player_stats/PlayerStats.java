package com.jack12324.som.capabilities.player_stats;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class PlayerStats implements IPlayerStats {

    private EntityPlayer player;
    private int experience;
    private int level;
    private BlockPos startPosition;

    public PlayerStats(EntityPlayer player) {
        this.player = player;
        experience = 0;
        this.level = 1;
        this.startPosition = new BlockPos(player.getEntityWorld().getSpawnPoint());
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
    public BlockPos getStartPosition() {
        return startPosition;
    }

    @Override
    public void setStartPosition(BlockPos position) {
        this.startPosition = position;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public int getExperience() {
        return experience;
    }
}
