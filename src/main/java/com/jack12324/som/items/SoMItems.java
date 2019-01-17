package com.jack12324.som.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class SoMItems {
    public static ItemBase modGui = new modGui("som_gui");

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                modGui
        );
    }

    public static void registerModels() {
        modGui.registerItemModel();
    }
}
