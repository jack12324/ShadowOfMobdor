package com.jack12324.som.gen;

import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class MobTracker {

    @SubscribeEvent
    public static void newPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.getEntityWorld().isRemote) {

            //if first time player, generate mobs todo place into default cap?
            if (event.player.getCapability(CapabilityHandler.NEM, null).isEmpty()) {
                for (int i = 0; i < 10; i++)
                    event.player.getCapability(CapabilityHandler.NEM, null).addMob(new EntitySoMZombie(event.player.getEntityWorld(),
                            event.player.getCapability(CapabilityHandler.XP, null).getLevel()), i);
            }
        }
        //testStatGen.testMobArray(mobs);
    }

    /*
    @SubscribeEvent
    public static void playerDied(LivingDeathEvent event) {
        if (!(event.getEntity().getEntityWorld().isRemote)) {
            if (event.getEntity() instanceof EntityPlayer) {


                EntityPlayer player = (EntityPlayer) event.getEntity();

                int index = players.indexOf(player.getUniqueID());
                for (int i = 0; i < mobs.get(index).length; i++) {
                    if (mobs.get(index)[i] == null)
                        mobs.get(index)[i] = new EntitySoMZombie(player.getEntityWorld(), playerLevels.get(index));
                    else
                        mobs.get(index)[i].reRoll(playerLevels.get(index)); //todo change this into event instead of automatic re roll
                }
                if (event.getSource().getTrueSource() instanceof EntitySoMZombie)
                    mobKilledPlayer((EntitySoMZombie) event.getSource().getTrueSource(), playerLevels.get(index));
            }
        }
    }
    */

    @SubscribeEvent
    public static void catchCrit(CriticalHitEvent event) {
        if (event.getTarget() instanceof EntitySoMZombie) {
            if (((EntitySoMZombie) event.getTarget()).getMobInv().contains(Invulnerabilities.CRITICAL))
                event.setDamageModifier(0);
        }
    }

    public static void mobKilledPlayer(EntitySoMZombie mob, int index) {
        mob.setDead();//todo make sure this doesn't drop anything
        mob.reRoll(index);//todo something special?
    }
/*
    public static void removeMob(EntitySoMZombie mob) {
        Random rand = new Random(); //todo add event to survive and level up
        for (EntitySoMZombie[] mobArray : mobs) {
            for (int i = 0; i < mobArray.length; i++) {
                if (mobArray[i].equals(mob))
                    mobs.get(mobs.indexOf(mobArray))[i] = null;
            }
        }
    }
    */
}
