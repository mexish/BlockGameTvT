package me.mexish.blockgametvt.command.impl;

import me.mexish.blockgametvt.command.Command;
import me.mexish.blockgametvt.util.ChatUtil;
import net.minecraft.command.ICommandSender;

public class TestCommand extends Command {

    @Override
    public String getCommandName() {
        return "testcmd";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        ChatUtil.base("Hi");
    }
}
