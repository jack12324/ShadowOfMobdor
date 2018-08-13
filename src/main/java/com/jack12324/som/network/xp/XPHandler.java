package com.jack12324.som.network.xp;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class XPHandler implements IMessageHandler<XPPacket, IMessage> {
    @Override
    public IMessage onMessage(XPPacket message, MessageContext ctx) {
        EntityPlayer player = ShadowOfMobdor.proxy.getPlayerFromContext(ctx);
        player.getCapability(CapabilityHandler.XP, null).setLevel(message.level);
        player.getCapability(CapabilityHandler.XP, null).setExperience(message.xp);
        return null;
    }
}
