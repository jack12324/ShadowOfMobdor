package com.jack12324.som.gen;


import com.jack12324.som.SoMConst;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;

import static com.jack12324.som.gen.MobTracker.playerLevels;

@Mod.EventBusSubscriber
public class Leveling {

    public static ArrayList<Integer> playerXP = new ArrayList<>();

    @SubscribeEvent
    public static void craftXP(PlayerEvent.ItemCraftedEvent event) {
        int index = MobTracker.players.indexOf(event.player.getUniqueID());
        playerXP.set(index, playerXP.get(index) + 1);
        if (checkLevelUp(index))
            levelUp(index);

    }

    @SubscribeEvent
    public static void SmeltXP(PlayerEvent.ItemSmeltedEvent event) {
        int index = MobTracker.players.indexOf(event.player.getUniqueID());
        playerXP.set(index, playerXP.get(index) + 1);
        if (checkLevelUp(index))
            levelUp(index);

    }

    @SubscribeEvent
    public static void killXP(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            int index = MobTracker.players.indexOf(player.getUniqueID());
            int xpAmount = (int) (event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
            playerXP.set(index, playerXP.get(index) + xpAmount);
            if (checkLevelUp(index))
                levelUp(index);

        }
    }

    /**
     * checks if the player has enough XP to level up
     *
     * @param index index of player to check
     * @return true if the player is below level 99 and has sufficient XP to level up, false otherwise
     */
    private static boolean checkLevelUp(int index) {
        if (playerLevels.get(index) < 99) {
            return playerXP.get(index) >= SoMConst.levels[playerLevels.get(index)];
        }
        return false;
    }

    private static void levelUp(int index) {
        playerLevels.set(index, playerLevels.get(index) + 1);
        int remainder = playerXP.get(index) - SoMConst.levels[playerLevels.get(index)];
        playerXP.set(index, remainder);
    }
}
