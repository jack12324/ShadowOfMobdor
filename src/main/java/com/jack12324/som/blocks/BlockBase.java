package com.jack12324.som.blocks;

import com.jack12324.som.ShadowOfMobdor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public abstract class BlockBase extends Block {
    protected String name;
    public BlockBase(String name) {
        this(name, Material.ROCK);
    }

    public BlockBase(String name, Material material) {
        super(material);
        setUnlocalizedName(ShadowOfMobdor.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
        this.name = name;
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    public void registerIemModel(Item itemBlock) {
        ShadowOfMobdor.proxy.registerItemRenderer(itemBlock, 0, name);
    }
}
