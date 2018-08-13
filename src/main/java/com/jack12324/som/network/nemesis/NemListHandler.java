package com.jack12324.som.network.nemesis;


import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class NemListHandler implements IMessageHandler<NemListPacket, IMessage> {

    @Override
    public IMessage onMessage(NemListPacket message, MessageContext ctx) {
        EntityPlayer player = ShadowOfMobdor.proxy.getPlayerFromContext(ctx);
        for (int i = 0; i < 10; i++)
            player.getCapability(CapabilityHandler.NEM, null).addMob((EntitySoMZombie) EntityList.createEntityFromNBT(message.mobs[i], player.getEntityWorld()), i);
        return null;
    }
}
