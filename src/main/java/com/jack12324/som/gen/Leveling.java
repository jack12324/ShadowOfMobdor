package com.jack12324.som.gen;


import com.jack12324.som.SoMConst;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.capabilities.player_stats.IPlayerStats;
import com.jack12324.som.network.SoMPacketHandler;
import com.jack12324.som.network.xp.XPPacket;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


@Mod.EventBusSubscriber
public class Leveling {

    /**
     * Adds one xp for each crafting event
     *
     * @param event event containing data
     */
    @SubscribeEvent
    public static void craftXP(PlayerEvent.ItemCraftedEvent event) {
        incrementXP(event);
    }

    /**
     * adds one xp for each smelt
     * @param event event containing data
     */
    @SubscribeEvent
    public static void SmeltXP(PlayerEvent.ItemSmeltedEvent event) {
        incrementXP(event);
    }

    private static void incrementXP(PlayerEvent event) {
        if (!event.player.getEntityWorld().isRemote) {
            IPlayerStats capability = event.player.getCapability(CapabilityHandler.XP, null);
            capability.addExperience(1);
            if (checkLevelUp(capability))
                levelUp(capability);
            SoMPacketHandler.NETWORK.sendTo(new XPPacket(capability.getExperience(), capability.getLevel()), (EntityPlayerMP) event.player);
        }
    }

    /**
     * adds one xp for each health point of a killed mob
     * @param event event containing data
     */
    @SubscribeEvent
    public static void killXP(LivingDeathEvent event) {

        if (!event.getEntity().getEntityWorld().isRemote) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                IPlayerStats cap = event.getSource().getTrueSource().getCapability(CapabilityHandler.XP, null);
                int xpAmount = (int) (event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
                cap.addExperience(xpAmount);
                if (checkLevelUp(cap))
                    levelUp(cap);
                SoMPacketHandler.NETWORK.sendTo(new XPPacket(cap.getExperience(), cap.getLevel()), (EntityPlayerMP) event.getSource().getTrueSource());

            }

        }
    }

    /**
     * checks if the player has enough XP to level up
     *
     * @param capability the player capability containing the xp and level information
     * @return true if the player is below level 99 and has sufficient XP to level up, false otherwise
     */
    private static boolean checkLevelUp(IPlayerStats capability) {
        if (capability.getLevel() < 99) {
            return capability.getExperience() >= SoMConst.levels[capability.getLevel()];
        }
        return false;
    }

    /**
     * levels the player up by one level and sets the remainder
     *
     * @param capability the player capability containing xp and level info
     */
    private static void levelUp(IPlayerStats capability) {
        int remainder = capability.getExperience() - SoMConst.levels[capability.getLevel()];
        capability.setLevel(capability.getLevel() + 1);
        capability.setExperience(remainder);
    }
}
