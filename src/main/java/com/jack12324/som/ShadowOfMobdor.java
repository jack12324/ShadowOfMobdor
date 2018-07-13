package com.jack12324.som;

import com.jack12324.som.blocks.SoMBlocks;
import com.jack12324.som.capabilities.nemesis_cap.INemesisList;
import com.jack12324.som.capabilities.nemesis_cap.NemesisList;
import com.jack12324.som.capabilities.nemesis_cap.NemesisStorage;
import com.jack12324.som.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ShadowOfMobdor.MODID, name = ShadowOfMobdor.MODNAME, version = ShadowOfMobdor.MODVERSION, useMetadata = true)
public class ShadowOfMobdor {

    public static final String MODID = "som";
    public static final String MODNAME = "Shadow of Mobdor";
    public static final String MODVERSION = "0.0.1";

    @SidedProxy(clientSide = "com.jack12324.som.proxy.ClientProxy", serverSide = "com.jack12324.som.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ShadowOfMobdor instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        CapabilityManager.INSTANCE.register(INemesisList.class, new NemesisStorage(), () -> new NemesisList(null));
        proxy.preInit(event);
        logger.info("XXXpreinitXXX");


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);

        logger.info("XXXinitXXX");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
        logger.info("XXXpostinitXXX");
    }

    @Mod.EventBusSubscriber public static class RegistrationHandler {

        @SubscribeEvent public static void registerBlocks(RegistryEvent.Register<Block> event) {
            SoMBlocks.register(event.getRegistry());
        }

        @SubscribeEvent public static void registerItems(RegistryEvent.Register<Item> event) {
            SoMBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent public static void registerModels(ModelRegistryEvent event) {
            SoMBlocks.registerModels();
        }

        // ...

    }
}
