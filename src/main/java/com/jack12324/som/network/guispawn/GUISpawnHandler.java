package com.jack12324.som.network.guispawn;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GUISpawnHandler implements IMessageHandler<GUISpawnPacket, IMessage> {
    @Override
    public IMessage onMessage(GUISpawnPacket message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            EntityPlayer player = ShadowOfMobdor.proxy.getPlayerFromContext(ctx);
            EntitySoMZombie mob = player.getCapability(CapabilityHandler.NEM, null).getMob(message.spawnIndex);
            mob.setPosition(message.x, message.y, message.z);
            mob.onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(mob.getPosition()), null);
            player.getEntityWorld().spawnEntity(mob);
            return null;
        }
        return null;
    }
}
