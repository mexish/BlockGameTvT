package me.mexish.blockgametvt.util;

import me.mexish.blockgametvt.BlockGameTvT;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatUtil {

    public ChatUtil() {
        MinecraftForge.EVENT_BUS.register(this);

    }

    public static void base(String message) {

        IChatComponent chatMessage = new ChatComponentText(BlockGameTvT.PREFIX + " " + message);
        BlockGameTvT.mc.thePlayer.addChatMessage(chatMessage);
    }

    public static void debug(String message) {
        IChatComponent chatMessage = new ChatComponentText(BlockGameTvT.PREFIX + " " + message);
        BlockGameTvT.mc.thePlayer.addChatMessage(chatMessage);

    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent e) {
        String message = e.message.getUnformattedText();
        // debug("Received chat message");
        // TODO: Make a config which will control whether or not the user is in debug mode and maybe some additional settings to
        // TODO: the random team assigner

    }

    public static void sendCommand(String command, String arguments) {

    }


}
