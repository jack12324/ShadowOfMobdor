package com.jack12324.som.network.xp;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class XPPacket implements IMessage {
    int xp;
    int level;

    public XPPacket(int xp, int level) {
        this.xp = xp;
        this.level = level;
    }

    public XPPacket() {
        xp = -1;
        level = -1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        xp = buf.readInt();
        level = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(xp);
        buf.writeInt(level);
    }
}
