package com.jack12324.som.entity;

import com.jack12324.som.ShadowOfMobdor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    public static void init() {
        // Every entity in our mod has an ID (local to this mod)
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocation(ShadowOfMobdor.MODID, "entities"), EntitySoMZombie.class, "SoMZombie", id++, ShadowOfMobdor.instance, 64, 3, true, 0x996600, 0x00ff00);


        // This is the loot table for our mob
        LootTableList.register(EntitySoMZombie.LOOT);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySoMZombie.class, RenderSoMZombie.FACTORY);
    }
}
