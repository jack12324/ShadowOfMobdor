package com.jack12324.som.network;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.entity.EntitySoMZombie;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//todo register packet and add to right click block
public class XPToClient implements IMessage {

    int level;
    int xp;
    NBTTagCompound[] mobs;

    public XPToClient(int level, int xp, EntitySoMZombie[] mobs) {
        this.level = level;
        this.xp = xp;
        for (int i = 0; i < mobs.length; i++) {
            this.mobs[i] = mobs[i].serializeNBT();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.level = buf.readInt();
        this.xp = buf.readInt();
        for (int i = 0; i < 10; i++)
            mobs[i] = ByteBufUtils.readTag(buf);


    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(level);
        buf.writeInt(xp);
        for (NBTTagCompound tag : mobs)
            ByteBufUtils.writeTag(buf, tag);
    }

    public static class mhandl implements IMessageHandler<XPToClient, IMessage> {

        @Override
        public IMessage onMessage(XPToClient message, MessageContext ctx) {
            EntityPlayer player = ShadowOfMobdor.proxy.getPlayerFromContext(ctx);
            player.getCapability(CapabilityHandler.XP, null).setLevel(message.level);
            player.getCapability(CapabilityHandler.XP, null).setExperience(message.xp);
            for (int i = 0; i < 10; i++)
                player.getCapability(CapabilityHandler.NEM, null).addMob((EntitySoMZombie) EntityList.createEntityFromNBT(message.mobs[i], player.getEntityWorld()), i);
            return null;
        }
    }
}
