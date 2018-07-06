package com.jack12324.som.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer))
            return;
//        else {
//            System.out.print(event.getObject());
//            event.addCapability(new ResourceLocation(ShadowOfMobdor.MODID, "nemesis"), new NemesisProvider());
//        }
//       // ((event.getObject())).getCapability(NemesisProvider.NEM, null).setWorld(event.getObject().getEntityWorld());
//    }
        return;
    }
}
