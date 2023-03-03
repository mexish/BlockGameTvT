package me.mexish.blockgametvt.util;

import me.mexish.blockgametvt.BlockGameTvT;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class HypixelUtil {

    public HypixelUtil() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    static boolean isOnHypixel = false;

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        ServerData data = BlockGameTvT.mc.getCurrentServerData();
        if (data != null && data.serverIP.contains("hypixel.net")) {
            isOnHypixel = true;
        }
    }
    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        isOnHypixel = false;
    }

}
