package com.jack12324.som.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class SoMBlocks {
    @GameRegistry.ObjectHolder("som:testspawner")
    public static BlockTestSpawner testSpawner;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testSpawner.initModel();
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.registerAll(
                testSpawner
        );

        GameRegistry.registerTileEntity(testSpawner.getTileEntityClass(), testSpawner.getUnlocalizedName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                testSpawner.createItemBlock()
        );
    }

}
