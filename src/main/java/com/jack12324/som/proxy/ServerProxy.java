package com.jack12324.som.proxy;

import com.jack12324.som.blocks.SoMBlocks;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ServerProxy implements CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModEntities.init();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        SoMBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        SoMBlocks.registerItemBlocks(event.getRegistry());
    }

    public void openGUI(int id, EntityPlayer player, EntitySoMZombie mob) {
    }
}
