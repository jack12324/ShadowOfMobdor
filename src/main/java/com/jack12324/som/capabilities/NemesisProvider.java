package com.jack12324.som.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Mana provider
 * <p>
 * This class is responsible for providing a capability. Other modders may
 * attach their own provider with implementation that returns another
 * implementation of IMana to the target's (Entity, TE, ItemStack, etc.) disposal.
 */
public class NemesisProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(INemesisList.class)
    public static final Capability<INemesisList> NEM = null;

    private INemesisList instance = NEM.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == NEM;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == NEM ? (T) NEM : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return NEM.getStorage().writeNBT(NEM, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        NEM.getStorage().readNBT(NEM, this.instance, null, nbt);
    }
}