package me.mexish.blockgametvt;

import me.mexish.blockgametvt.command.Command;
import me.mexish.blockgametvt.command.impl.ButtonActionCommand;
import me.mexish.blockgametvt.command.impl.RandomTeamCommand;
import me.mexish.blockgametvt.command.impl.TestCommand;
import me.mexish.blockgametvt.util.ChatUtil;
import me.mexish.blockgametvt.util.HypixelUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * @author mexish
 * */
@Mod(modid = BlockGameTvT.MODID, version = BlockGameTvT.VERSION)
public class BlockGameTvT
{
    public static final String MODID = "blockgametvt";
    public static final String VERSION = "1.0";

    public static final String PREFIX = "§8[§4BGTVT§8]";

    public static final Minecraft mc = Minecraft.getMinecraft();
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        registerCommands();
        registerListeners();
    }

    public void registerListeners() {
        new ChatUtil();
        new HypixelUtil();
    }

    public void registerCommands() {
        new TestCommand();
        new RandomTeamCommand();
        new ButtonActionCommand();
    }
}
