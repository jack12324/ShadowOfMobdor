package com.jack12324.som.capabilities;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.experience.Experience;
import com.jack12324.som.capabilities.experience.ExperienceProvider;
import com.jack12324.som.capabilities.experience.IExperience;
import com.jack12324.som.capabilities.nemesis.INemesisList;
import com.jack12324.som.capabilities.nemesis.NemesisList;
import com.jack12324.som.capabilities.nemesis.NemesisProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
    public static final Capability<IExperience> XP = null;

    @SubscribeEvent
    public static void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer))
            return;
        else {
            final NemesisList NEMS = new NemesisList(event.getObject().getEntityWorld());
            event.addCapability(new ResourceLocation(ShadowOfMobdor.MODID, "nemesis"), new NemesisProvider(NEM, NEMS));

            final Experience SOMXP = new Experience((EntityPlayer) event.getObject());
            event.addCapability(new ResourceLocation(ShadowOfMobdor.MODID, "experience"), new ExperienceProvider(XP, SOMXP));
        }
    }
}

