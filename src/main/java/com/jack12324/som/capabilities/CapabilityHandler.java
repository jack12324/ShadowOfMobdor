package com.jack12324.som.capabilities;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.nemesis.INemesisList;
import com.jack12324.som.capabilities.nemesis.NemesisList;
import com.jack12324.som.capabilities.nemesis.NemesisProvider;
import com.jack12324.som.capabilities.player_stats.IPlayerStats;
import com.jack12324.som.capabilities.player_stats.PlayerStats;
import com.jack12324.som.capabilities.player_stats.PlayerStatsProvider;
import com.jack12324.som.network.SoMPacketHandler;
import com.jack12324.som.network.nemesis.NemListPacket;
import com.jack12324.som.network.player_stats.PSPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Capability handler
 * <p>
 * This class is responsible for attaching our capabilities
 */
@Mod.EventBusSubscriber
public class CapabilityHandler {
    @CapabilityInject(INemesisList.class)
    public static final Capability<INemesisList> NEM = null;
    @CapabilityInject(IPlayerStats.class)
    public static final Capability<IPlayerStats> XP = null;

    @SubscribeEvent
    public static void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
        ShadowOfMobdor.logger.debug("AttachCapabilitiesEvent");
        if (!(event.getObject() instanceof EntityPlayer))
            return;
        else {

            final PlayerStats SOMXP = new PlayerStats((EntityPlayer) event.getObject());
            event.addCapability(new ResourceLocation(ShadowOfMobdor.MODID, "experience"), new PlayerStatsProvider(XP, SOMXP));

            final NemesisList NEMS = new NemesisList((EntityPlayer) event.getObject());
            event.addCapability(new ResourceLocation(ShadowOfMobdor.MODID, "nemesis"), new NemesisProvider(NEM, NEMS));

        }
    }

    /**
     * copies capabilities on player death or return from end
     *
     * @param event
     */
    @SubscribeEvent
    public static void playerClone(final PlayerEvent.Clone event) {
        final IPlayerStats oldXP = event.getOriginal().getCapability(XP, null);
        final IPlayerStats newXP = event.getEntityPlayer().getCapability(XP, null);
        final INemesisList oldNem = event.getOriginal().getCapability(NEM, null);
        final INemesisList newNem = event.getEntityPlayer().getCapability(NEM, null);

        if (newXP != null && oldXP != null) {
            newXP.setExperience(oldXP.getExperience());
            newXP.setLevel(oldXP.getLevel());
        }

        if (newNem != null && oldNem != null)
            newNem.copyList(oldNem.getMobs());

        if (!event.getEntityPlayer().getEntityWorld().isRemote) {
            SoMPacketHandler.NETWORK.sendTo(new NemListPacket(event.getEntityPlayer().getCapability(CapabilityHandler.NEM, null).getMobs()), (EntityPlayerMP) event.getEntityPlayer());
            IPlayerStats capability = event.getEntityPlayer().getCapability(CapabilityHandler.XP, null);
            SoMPacketHandler.NETWORK.sendTo(new PSPacket(capability.getExperience(), capability.getLevel(), capability.getStartPosition()), (EntityPlayerMP) event.getEntityPlayer());
        }
    }
}

