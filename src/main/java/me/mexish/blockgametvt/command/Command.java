package me.mexish.blockgametvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.client.ClientCommandHandler;

public abstract class Command extends CommandBase implements ICommand {
    String commandName;
    String commandUsage;

    public Command() {
        ClientCommandHandler.instance.registerCommand(this);
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return commandUsage;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {}
}
