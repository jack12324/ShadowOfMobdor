package com.jack12324.som.capabilities.player_stats;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerStatsProvider implements ICapabilitySerializable<NBTBase> {
    protected final Capability<IPlayerStats> SOMPD;
    protected final IPlayerStats instance;

    public PlayerStatsProvider(Capability<IPlayerStats> capability, IPlayerStats instance) {
        this.SOMPD = capability;
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


    public Capability<IPlayerStats> getCapability() {
        return SOMPD;
    }

    public IPlayerStats getInstance() {
        return instance;
    }
}
