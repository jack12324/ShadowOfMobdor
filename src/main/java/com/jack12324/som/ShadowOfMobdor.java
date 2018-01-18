package com.jack12324.som;

import com.jack12324.som.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
        proxy.preInit(event);

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}