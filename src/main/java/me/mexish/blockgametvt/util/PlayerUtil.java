package me.mexish.blockgametvt.util;

import lombok.NonNull;
import me.mexish.blockgametvt.BlockGameTvT;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.regex.Pattern;


public class PlayerUtil {

    public static boolean isPlayerInGame() {
        return BlockGameTvT.mc.thePlayer != null && BlockGameTvT.mc.theWorld != null;
    }


}
