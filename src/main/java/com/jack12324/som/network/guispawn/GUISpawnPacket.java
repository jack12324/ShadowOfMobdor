package com.jack12324.som.network.guispawn;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class GUISpawnPacket implements IMessage {
    int x;
    int y;
    int z;
    int spawnIndex;

    public GUISpawnPacket(int x, int y, int z, int spawnIndex) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.spawnIndex = spawnIndex;
    }

    public GUISpawnPacket() {
        x = 0;
        y = 0;
        z = 0;
        spawnIndex = -1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        spawnIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(spawnIndex);
    }
}
