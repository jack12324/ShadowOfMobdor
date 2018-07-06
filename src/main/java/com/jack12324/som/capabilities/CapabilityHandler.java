package com.jack12324.som.capabilities;

import com.jack12324.som.ShadowOfMobdor;
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

    @SubscribeEvent
    public static void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer))
            return;
        else {
            final NemesisList nems = new NemesisList(event.getObject().getEntityWorld());
            event.addCapability(new ResourceLocation(ShadowOfMobdor.MODID, "nemesis"), new NemesisProvider(NEM, nems));
        }
    }
}

