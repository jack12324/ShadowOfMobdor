package com.jack12324.som.capabilities.nemesis;

import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class NemesisStorage implements Capability.IStorage<INemesisList> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<INemesisList> capability, INemesisList instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        if (!instance.isEmpty()) {
            for (int i = 0; i < instance.getMobs().length; i++)
                nbt.setTag(i + "", instance.getMob(i).serializeNBT());
        }

        return nbt;
    }

    @Override
    public void readNBT(Capability<INemesisList> capability, INemesisList instance, EnumFacing side, NBTBase nbt) {
        instance.clearMobs();
        for (int i = 0; i < instance.getMobs().length; i++)
            instance.addMob((EntitySoMZombie) EntityList.createEntityFromNBT(((NBTTagCompound) nbt).getCompoundTag(i + ""), instance.getWorld()), i);
    }
}
