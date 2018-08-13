package com.jack12324.som.network;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.network.guispawn.GUISpawnHandler;
import com.jack12324.som.network.guispawn.GUISpawnPacket;
import com.jack12324.som.network.nemesis.NemListHandler;
import com.jack12324.som.network.nemesis.NemListPacket;
import com.jack12324.som.network.xp.XPHandler;
import com.jack12324.som.network.xp.XPPacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class SoMPacketHandler {
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(ShadowOfMobdor.MODID);
    static int NETID = 0;

    public static void registerMessages() {
        NETWORK.registerMessage(NemListHandler.class, NemListPacket.class, NETID++, Side.CLIENT);
        NETWORK.registerMessage(NemListHandler.class, NemListPacket.class, NETID++, Side.SERVER);
        NETWORK.registerMessage(XPHandler.class, XPPacket.class, NETID++, Side.CLIENT);
        NETWORK.registerMessage(XPHandler.class, XPPacket.class, NETID++, Side.SERVER);
        NETWORK.registerMessage(GUISpawnHandler.class, GUISpawnPacket.class, NETID++, Side.CLIENT);
        NETWORK.registerMessage(GUISpawnHandler.class, GUISpawnPacket.class, NETID++, Side.SERVER);
    }
}
