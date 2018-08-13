package com.jack12324.som.network.nemesis;

import com.jack12324.som.entity.EntitySoMZombie;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

//todo register packet and add to right click block
public class NemListPacket implements IMessage {

    int arraySize;
    NBTTagCompound[] mobs;

    public NemListPacket(EntitySoMZombie[] mobs) {
        this.arraySize = mobs.length;
        this.mobs = new NBTTagCompound[mobs.length];
        for (int i = 0; i < mobs.length; i++) {
            this.mobs[i] = mobs[i].serializeNBT();
        }
    }

    public NemListPacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.arraySize = buf.readInt();
        this.mobs = new NBTTagCompound[arraySize];
        for (int i = 0; i < this.mobs.length; i++)
            mobs[i] = ByteBufUtils.readTag(buf);


    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(arraySize);
        for (NBTTagCompound tag : mobs)
            ByteBufUtils.writeTag(buf, tag);
    }
}
