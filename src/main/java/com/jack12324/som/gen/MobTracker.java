package com.jack12324.som.gen;

import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.capabilities.experience.IExperience;
import com.jack12324.som.capabilities.nemesis.INemesisList;
import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Random;

@Mod.EventBusSubscriber
public class MobTracker {

    @SubscribeEvent
    public static void newPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.getEntityWorld().isRemote) {

            //if first time player, generate mobs todo place into default cap?
            if (event.player.getCapability(CapabilityHandler.NEM, null).isEmpty()) {
                for (int i = 0; i < 10; i++)
                    event.player.getCapability(CapabilityHandler.NEM, null).addMob(new EntitySoMZombie(event.player), i);
            }
        }
    }


    @SubscribeEvent
    public static void playerDied(LivingDeathEvent event) {
        //server side
        if (!(event.getEntity().getEntityWorld().isRemote)) {
            //is a player
            if (event.getEntity() instanceof EntityPlayer) {

                EntityPlayer player = (EntityPlayer) event.getEntity();
                IExperience playerXP = player.getCapability(CapabilityHandler.XP, null);
                INemesisList mobs = player.getCapability(CapabilityHandler.NEM, null);

                //iterate over every mod tied to player
                for (int i = 0; i < mobs.getMobs().length; i++) {
                    EntitySoMZombie target = mobs.getMob(i);

                    //add mob if they have been removed
                    if (target == null)
                        mobs.addMob(new EntitySoMZombie(player), i);

                        //attempt to revive killed mobs, otherwise generate new
                    else if (target.isKilled()) {
                        Random rand = new Random();

                        if (rand.nextInt(100) < 50) { //todo make something better than random chance
                            target.setKilled(false);
                            target.setHealth(target.getMaxHealth());
                            target.reRoll(playerXP.getLevel(), 2);
                        } else
                            mobs.addMob(new EntitySoMZombie(player), i);
                    } else
                        target.reRoll(playerXP.getLevel()); //todo change this into event instead of automatic re roll
                }
                if (event.getSource().getTrueSource() instanceof EntitySoMZombie)
                    mobKilledPlayer((EntitySoMZombie) event.getSource().getTrueSource(), playerXP.getLevel());
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

    public static void mobKilledPlayer(EntitySoMZombie mob, int level) {
        mob.setDead();//todo make sure this doesn't drop anything
        mob.reRoll(level, 4);//todo something special?
    }

    public static void removeMob(EntityPlayer player, EntitySoMZombie mob) {
        Random rand = new Random(); //todo add event to survive and level up
        player.getCapability(CapabilityHandler.NEM, null).removeMob(mob);
    }
}
