package com.jack12324.som.items;

import com.jack12324.som.ShadowOfMobdor;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item {
    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public void registerItemModel() {
        ShadowOfMobdor.proxy.registerItemRenderer(this, 0, name);
    }
}
