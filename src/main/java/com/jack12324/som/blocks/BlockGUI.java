package com.jack12324.som.blocks;

import com.jack12324.som.ShadowOfMobdor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGUI extends BlockBase {
    public BlockGUI() {
        super("gui", Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
            } else {
                ShadowOfMobdor.proxy.openGUI(0, player, null);
            }
        }
        return true;
    }
}
