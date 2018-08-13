package com.jack12324.som.blocks;

import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.network.SoMPacketHandler;
import com.jack12324.som.network.xp.XPPacket;
import com.jack12324.som.tiles.TileEntityTestSpawner;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTestSpawner extends BlockTE<TileEntityTestSpawner> {

    public BlockTestSpawner() {
        super("testspawner", Material.ROCK);
    }

    @Nullable
    @Override
    public TileEntityTestSpawner createTileEntity(World world, IBlockState state) {
        return new TileEntityTestSpawner();
    }

    @Override
    public Class<TileEntityTestSpawner> getTileEntityClass() {
        return TileEntityTestSpawner.class;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntityTestSpawner tile = this.getTileEntity(world, pos);
            if (side != EnumFacing.UP) {
                int counter;
                if (hitY < .5f) {
                    tile.decrement();
                } else {
                    tile.increment();
                }
                TextComponentTranslation component = new TextComponentTranslation("message.som.spawn_lvl", tile.getLevel());
                player.sendStatusMessage(component, false);
            } else {
                TextComponentTranslation component = new TextComponentTranslation("message.som.spawning", tile.getLevel());
                player.sendStatusMessage(component, false);
                player.getCapability(CapabilityHandler.XP, null).setLevel(tile.getLevel());
                SoMPacketHandler.NETWORK.sendTo(new XPPacket(player.getCapability(CapabilityHandler.XP, null).getExperience(), player.getCapability(CapabilityHandler.XP, null).getLevel()), (EntityPlayerMP) player);
                EntitySoMZombie mob = new EntitySoMZombie(player);
                mob.setPosition(tile.getPos().getX(), tile.getPos().getY() + 2,
                                tile.getPos().getZ());
                mob.onInitialSpawn(world.getDifficultyForLocation(tile.getPos()), null);
                mob.print();
                world.spawnEntity(mob);
            }
        }
        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }

}
