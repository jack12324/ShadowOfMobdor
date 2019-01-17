package com.jack12324.som.items;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.capabilities.nemesis.INemesisList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.jack12324.som.SoMConst.GUI_MAIN;
import static com.jack12324.som.SoMConst.GUI_START;

public class modGui extends ItemBase {
    public modGui(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            INemesisList nemCap = playerIn.getCapability(CapabilityHandler.NEM, null);
            //no nemesis list exists yet
            if (nemCap.isEmpty())
                ShadowOfMobdor.proxy.openGUI(GUI_START, playerIn, -1);
            else
                ShadowOfMobdor.proxy.openGUI(GUI_MAIN, playerIn, -1);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
