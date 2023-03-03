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
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author mexish
 * */
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
    /**
     * @param type type of the error, example: 1 - Wrong usage, 2 - Both maps of the red and the blue team are empty.
     * */
    public static void error(int type) {
        IChatComponent chatMessage = new ChatComponentText("");
        if (type == 1) {
            chatMessage = new ChatComponentText("§4[ERROR] §cWrong usage!");
        }
        if (type == 2) {
            chatMessage = new ChatComponentText("§4[ERROR] §cBoth the Red and the Blue teams are empty!");
        }
        if (type == 3) {
            chatMessage = new ChatComponentText("§4[ERROR] §cThe player ID list is empty!");
        }
        // TODO: Add more error types
        BlockGameTvT.mc.thePlayer.addChatMessage(chatMessage);
    }
    

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent e) {
        String message = e.message.getUnformattedText();
        // debug("Received chat message");
        // TODO: Make a config which will control whether or not the user is in debug mode and maybe some additional settings to
        // TODO: the random team assigner

    }


}
