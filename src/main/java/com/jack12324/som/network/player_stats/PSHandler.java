package com.jack12324.som.network.player_stats;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PSHandler implements IMessageHandler<PSPacket, IMessage> {
    @Override
    public IMessage onMessage(PSPacket message, MessageContext ctx) {
        EntityPlayer player = ShadowOfMobdor.proxy.getPlayerFromContext(ctx);
        player.getCapability(CapabilityHandler.XP, null).setLevel(message.level);
        player.getCapability(CapabilityHandler.XP, null).setExperience(message.xp);
        return null;
    }
}
