package com.jack12324.som.blocks;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.capabilities.nemesis.INemesisList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.jack12324.som.SoMConst.GUI_MAIN;
import static com.jack12324.som.SoMConst.GUI_START;

public class BlockGUI extends BlockBase {
    public BlockGUI() {
        super("gui", Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ShadowOfMobdor.logger.info("Server Mobs: " + player.getCapability(CapabilityHandler.NEM, null).getMobs());
            ShadowOfMobdor.logger.info("Server Level: " + player.getCapability(CapabilityHandler.XP, null).getLevel());
            ShadowOfMobdor.logger.info("Server XP: " + player.getCapability(CapabilityHandler.XP, null).getExperience());
            return true;
        } else {
            INemesisList nemCap = player.getCapability(CapabilityHandler.NEM, null);
            //no nemesis list exists yet
            if (nemCap.isEmpty())
                ShadowOfMobdor.proxy.openGUI(GUI_START, player, -1);
            else
                ShadowOfMobdor.proxy.openGUI(GUI_MAIN, player, -1);
            ShadowOfMobdor.logger.info("Client Mobs: " + player.getCapability(CapabilityHandler.NEM, null).getMobs());
            ShadowOfMobdor.logger.info("Client Level: " + player.getCapability(CapabilityHandler.XP, null).getLevel());
            ShadowOfMobdor.logger.info("Client XP: " + player.getCapability(CapabilityHandler.XP, null).getExperience());

            //   ShadowOfMobdor.proxy.openGUI(0, player, null);
            return true;
        }
    }
}
