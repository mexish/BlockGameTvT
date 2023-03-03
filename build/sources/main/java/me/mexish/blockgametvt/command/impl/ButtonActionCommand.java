package me.mexish.blockgametvt.command.impl;

import javafx.scene.input.ClipboardContent;
import me.mexish.blockgametvt.BlockGameTvT;
import me.mexish.blockgametvt.command.Command;
import me.mexish.blockgametvt.util.ChatUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ButtonActionCommand extends Command {

    @Override
    public String getCommandName() {
        return "bac";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("invoke")) {
                String team = args[1];
                String teamMembers = "";
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection ss;

                if (RandomTeamCommand.getTeamRedMap().isEmpty() || RandomTeamCommand.getTeamBlueMap().isEmpty()) {
                    ChatUtil.error(2);
                } else {
                    if (team.equalsIgnoreCase("red")) {
                        teamMembers = String.join(", ", RandomTeamCommand.getTeamRedMap().keySet());
                        BlockGameTvT.mc.thePlayer.sendChatMessage("/pc Red team: " + teamMembers);
                    }
                    if (team.equalsIgnoreCase("blue")) {
                        teamMembers = String.join(", ", RandomTeamCommand.getTeamBlueMap().keySet());
                        BlockGameTvT.mc.thePlayer.sendChatMessage("/pc Blue team: " + teamMembers);
                    }
                    ss = new StringSelection(teamMembers);
                    // Setting the contents of our clipboard
                    cb.setContents(ss, null);
                }
            }
        }
    }
}
