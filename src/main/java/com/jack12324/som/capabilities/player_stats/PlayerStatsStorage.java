package com.jack12324.som.capabilities.player_stats;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PlayerStatsStorage implements Capability.IStorage<IPlayerStats> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IPlayerStats> capability, IPlayerStats instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("xp", instance.getExperience());
        nbt.setInteger("level", instance.getLevel());
        BlockPos pos = instance.getStartPosition();
        nbt.setInteger("x", pos.getX());
        nbt.setInteger("y", pos.getY());
        nbt.setInteger("z", pos.getZ());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IPlayerStats> capability, IPlayerStats instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setExperience(tag.getInteger("xp"));
        instance.setLevel(tag.getInteger("level"));
        instance.setStartPosition(new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
    }
}
