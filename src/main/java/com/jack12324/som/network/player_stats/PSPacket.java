package com.jack12324.som.network.player_stats;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PSPacket implements IMessage {
    int xp;
    int level;
    BlockPos pos;

    public PSPacket(int xp, int level, BlockPos pos) {
        this.xp = xp;
        this.level = level;
        this.pos = pos;
    }

    public PSPacket() {
        xp = -1;
        level = -1;
        pos = new BlockPos(0, 0, 0);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        xp = buf.readInt();
        level = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        pos = new BlockPos(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(xp);
        buf.writeInt(level);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }
}
