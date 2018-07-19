package com.jack12324.som.capabilities.experience;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ExperienceStorage implements Capability.IStorage<IExperience> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IExperience> capability, IExperience instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("xp", instance.getExperience());
        nbt.setInteger("level", instance.getLevel());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IExperience> capability, IExperience instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setExperience(tag.getInteger("xp"));
        instance.setLevel(tag.getInteger("level"));
    }
}
