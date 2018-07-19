package com.jack12324.som.capabilities.experience;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ExperienceProvider implements ICapabilitySerializable<NBTBase> {//todo can make generic if need more capabilities
    protected final Capability<IExperience> SOMXP;
    protected final IExperience instance;

    public ExperienceProvider(Capability<IExperience> capability, IExperience instance) {
        this.SOMXP = capability;
        this.instance = instance;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == getCapability();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == getCapability() ? getCapability().cast(getInstance()) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return getCapability().writeNBT(getInstance(), null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        getCapability().readNBT(getInstance(), null, nbt);
    }


    public Capability<IExperience> getCapability() {
        return SOMXP;
    }

    public IExperience getInstance() {
        return instance;
    }
}