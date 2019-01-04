package com.jack12324.som.proxy;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.entity.ModEntities;
import com.jack12324.som.gui.GuiDESC;
import com.jack12324.som.gui.GuiGUI;
import com.jack12324.som.gui.GuiStart;
import com.jack12324.som.gui.GuiWarn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import static com.jack12324.som.SoMConst.GUI_START;
import static com.jack12324.som.SoMConst.GUI_WARN;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements CommonProxy {
    ServerProxy serverProxy = new ServerProxy();
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        serverProxy.preInit(event);
        ModEntities.initModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        serverProxy.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        serverProxy.postInit(event);
    }

    @Override public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                        new ModelResourceLocation(ShadowOfMobdor.MODID + ":" + id, "inventory"));
    }

    @Override
    public EntityPlayer getPlayerFromContext(MessageContext ctx) {
        if (ctx.side.isClient())
            return Minecraft.getMinecraft().player;
        else
            return ctx.getServerHandler().player;
    }

    @Override
    public void openGUI(int id, EntityPlayer player, int mobIndex) {
        switch (id) {//todo constant value for IDS for easier reading
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(new GuiGUI(player));
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(new GuiDESC(mobIndex, player));
                break;
            case GUI_WARN:
                Minecraft.getMinecraft().displayGuiScreen(new GuiWarn(player));
                break;
            case GUI_START:
                Minecraft.getMinecraft().displayGuiScreen(new GuiStart(player));
                break;
        }
    }
}
