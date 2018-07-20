package com.jack12324.som.gen;


import com.jack12324.som.SoMConst;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.capabilities.experience.IExperience;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
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
        IExperience capability = event.player.getCapability(CapabilityHandler.XP, null);
        capability.addExperience( 1);
        if (checkLevelUp(capability))
            levelUp(capability);
    }

    /**
     * adds one xp for each smelt
     * @param event event containing data
     */
    @SubscribeEvent
    public static void SmeltXP(PlayerEvent.ItemSmeltedEvent event) {
        IExperience capability = event.player.getCapability(CapabilityHandler.XP, null);
        capability.addExperience(1);
        if (checkLevelUp(capability))
            levelUp(capability);
    }

    /**
     * adds one xp for each health point of a killed mob
     * @param event event containing data
     */
    @SubscribeEvent
    public static void killXP(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            IExperience cap = event.getSource().getTrueSource().getCapability(CapabilityHandler.XP,null);
            int xpAmount = (int) (event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
            cap.addExperience(xpAmount);
            if (checkLevelUp(cap))
                levelUp(cap);
        }
    }

    /**
     * checks if the player has enough XP to level up
     *
     * @param capability the player capability containing the xp and level information
     * @return true if the player is below level 99 and has sufficient XP to level up, false otherwise
     */
    private static boolean checkLevelUp(IExperience capability) {
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
    private static void levelUp(IExperience capability) {
        int remainder = capability.getExperience() - SoMConst.levels[capability.getLevel()];
        capability.setLevel(capability.getLevel() + 1);
        capability.setExperience(remainder);
    }
}
