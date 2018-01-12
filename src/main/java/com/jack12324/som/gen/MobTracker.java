package com.jack12324.som.gen;

import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.Random;

@Mod.EventBusSubscriber
public class MobTracker {
    private static ArrayList<EntitySoMZombie[]> mobs = new ArrayList<>();
    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<Integer> playerLevels = new ArrayList<>();

    @SubscribeEvent
    public static void newPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        if (!players.contains(event.player) && !event.player.getEntityWorld().isRemote) ;
        {
            playerLevels.add(0);
            players.add(event.player.getDisplayNameString());
            mobs.add(new EntitySoMZombie[10]);
            for (EntitySoMZombie mob : mobs.get(mobs.size() - 1))
                mob = new EntitySoMZombie(event.player.getEntityWorld(), playerLevels.get(playerLevels.size() - 1));
        }
    }

    @SubscribeEvent
    public static void playerDied(LivingDeathEvent event) {
        if (!(event.getEntity().getEntityWorld().isRemote)) {
            if (event.getEntity() instanceof EntityPlayer) {
                if (event.getSource().getTrueSource() instanceof EntitySoMZombie)
                    mobKilledPlayer((EntitySoMZombie) event.getSource().getTrueSource());

                EntityPlayer player = (EntityPlayer) event.getEntity();

                int index = players.indexOf(player.getDisplayNameString());
                for (int i = 0; i < mobs.get(index).length; i++) {
                    if (mobs.get(index)[i] == null)
                        mobs.get(index)[i] = new EntitySoMZombie(player.getEntityWorld(), playerLevels.get(index));
                    else
                        mobs.get(index)[i].reRoll(); //todo change this into event instead of automatic re roll
                }
            }
        }
    }

    @SubscribeEvent
    public static void catchCrit(CriticalHitEvent event) {
        if (event.getTarget() instanceof EntitySoMZombie) {
            if (((EntitySoMZombie) event.getTarget()).getMobInv().contains(Invulnerabilities.CRITICAL))
                event.setDamageModifier(0);
        }
    }

    public static void mobKilledPlayer(EntitySoMZombie mob) {
        mob.setDead();//todo make sure this doesn't drop anything
        mob.reRoll();
    }

    public static void removeMob(EntitySoMZombie mob) {
        Random rand = new Random(); //todo add event to survive and level up
        for (EntitySoMZombie[] mobArray : mobs) {
            for (int i = 0; i < mobArray.length; i++) {
                if (mobArray[i].equals(mob))
                    mobs.get(mobs.indexOf(mobArray))[i] = null;
            }
        }
    }

}
