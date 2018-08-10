package com.jack12324.som.proxy;

import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public interface CommonProxy {
    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void openGUI(int id, EntityPlayer player, EntitySoMZombie mob);

    void registerItemRenderer(Item item, int meta, String id);

    EntityPlayer getPlayerFromContext(MessageContext ctx);
}
