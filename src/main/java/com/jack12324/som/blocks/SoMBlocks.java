package com.jack12324.som.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class SoMBlocks {
    @GameRegistry.ObjectHolder("som:testspawner")
    public static BlockTestSpawner testSpawner = new BlockTestSpawner();
    public static BlockGUI gui = new BlockGUI();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(testSpawner, gui
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(testSpawner.createItemBlock(), gui.createItemBlock()
        );
    }

    public static void registerModels() {
        testSpawner.registerIemModel(Item.getItemFromBlock(testSpawner));
        gui.registerIemModel(Item.getItemFromBlock(gui));
    }

}
