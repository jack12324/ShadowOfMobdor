package com.jack12324.som.proxy;

import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.entity.ModEntities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class ServerProxy implements CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModEntities.init();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void openGUI(int id, EntityPlayer player, EntitySoMZombie mob) {
    }
}
