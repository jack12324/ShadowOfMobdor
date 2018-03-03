package com.jack12324.som.proxy;

import com.jack12324.som.blocks.SoMBlocks;
import com.jack12324.som.entity.ModEntities;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements CommonProxy {
    ServerProxy serverProxy = new ServerProxy();
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        serverProxy.preInit(event);
        ModEntities.initModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        serverProxy.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        serverProxy.postInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        SoMBlocks.initModels();
    }
}
