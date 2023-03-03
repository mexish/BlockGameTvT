package me.mexish.blockgametvt.command.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import me.mexish.blockgametvt.BlockGameTvT;
import me.mexish.blockgametvt.command.Command;
import me.mexish.blockgametvt.type.PlayerProcessingRecord;
import me.mexish.blockgametvt.util.ChatUtil;
import me.mexish.blockgametvt.util.NumberUtil;
import me.mexish.blockgametvt.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * @author mexish
 * */

public class RandomTeamCommand extends Command {

    public RandomTeamCommand() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Getter static Map<String, Integer> playerIds = new HashMap<>();

    Collection<PlayerProcessingRecord> processing = Collections.synchronizedCollection(new HashSet<>());

    @Getter static int playerAmount;
    String redTeamMembers;
    String blueTeamMembers;


    @Override
    public String getCommandName() {
        return "randomteam";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("rt");
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("cl")) {
                playerIds.clear();
                ChatUtil.base("Cleared all ids!");
                return;
            }

            if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("red")) {
                if (!playerIds.isEmpty()) {
                    ChatUtil.base("Team §cRed: §3" + redTeamMembers);
                } else {
                    ChatUtil.base("§cThe player ID list is empty!");
                }
            }
            if (args[0].equalsIgnoreCase("b") || args[0].equalsIgnoreCase("blue")) {
                if (!playerIds.isEmpty()) {
                    ChatUtil.base("Team §1Blue: §3" + blueTeamMembers);
                } else {
                    ChatUtil.base("§cThe player ID list is empty!");
                }

            }


            try {
                playerAmount = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                ChatUtil.base("§4Wrong usage! The argument must be an Integer! Example: /randomteam 10");
                return;
            }

            for (val ent : Minecraft.getMinecraft().theWorld.getEntities(EntityPlayer.class, ($) -> true)) {
                //ChatUtil.base("Break 1");
                val isBot = bot(ent);
                if (ent.getName() != null) {
                    //ChatUtil.debug(ent.getName() + (isBot ? " - BOT" : ""));
                }

                if (isBot) {
                    continue;
                }
                if (playerIds.size() == 12) {
                    return;
                }

                if (isPlayerListFull()) {
                    //ChatUtil.base("Player list full!");
                    return;
                }
                if (!PlayerUtil.isPlayerInGame()) {
                    return;
                }

                try {
                    processing.add(new PlayerProcessingRecord(ent.getUniqueID(), System.currentTimeMillis(), Executors.newFixedThreadPool(1).submit(() -> {
                        if (!playerIds.containsKey(ent.getName())) {
                            //ChatUtil.base("Assigning random team to player: " + ent.getName());
                            int playerId = NumberUtil.generateRandomPlayerId(playerAmount);
                            playerIds.put(ent.getName(), playerId);
                            //ChatUtil.base("Assigned id §0" + playerId + "§7: " + ent.getName());

                        }
                    })));
                } catch (Throwable t) {
                    t.printStackTrace();
                }

            }
            if (!playerIds.isEmpty()) {
                List<Map.Entry<String, Integer>> entryList = new ArrayList<>(playerIds.entrySet());
                entryList.sort(Map.Entry.comparingByValue());

                Map<String, Integer> sortedIdMap = new LinkedHashMap<>();

                for (Map.Entry<String, Integer> entry : entryList) {
                    sortedIdMap.put(entry.getKey(), entry.getValue());
                }

                Map<String, Integer> teamRedMap = new HashMap<>();
                Map<String, Integer> teamBlueMap = new HashMap<>();

                for (Map.Entry<String, Integer> entry : sortedIdMap.entrySet()) {
                    String name = entry.getKey();
                    int id = entry.getValue();

                    if (id >= 1 && id <= Math.round(playerAmount) / 2) {
                        if (!(teamRedMap.size() > Math.round(playerAmount) / 2)) {
                            teamRedMap.put(name, id);
                        }

                    } else if (id >= Math.round(playerAmount) / 2 + 1 && id <= playerAmount) {
                        if (!(teamRedMap.size() > Math.round(playerAmount) / 2)) {
                            teamBlueMap.put(name, id);
                        }
                    }
                }
                redTeamMembers = String.join(", ", teamRedMap.keySet());
                blueTeamMembers = String.join(", ", teamBlueMap.keySet());

                ChatComponentText btnText = new ChatComponentText("§bCLICK");
                ChatComponentText btnHoverText = new ChatComponentText("Copy team members");

                ChatComponentTranslation buttonRed = new ChatComponentTranslation("chat.type.announcement", btnText);
                ChatComponentTranslation buttonBlue = new ChatComponentTranslation("chat.type.announcement", btnText);
                buttonRed.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, btnHoverText));
                buttonBlue.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, btnHoverText));
                //BlockGameTvT.mc.thePlayer.sendChatMessage("Team Red: " + redTeamMembers);
                //BlockGameTvT.mc.thePlayer.sendChatMessage("Team Red: " + blueTeamMembers);

                IChatComponent finalComponentText1 = new ChatComponentText("Team §cRed: §3" + redTeamMembers);
                IChatComponent finalComponentText2 = new ChatComponentText("Team §1Blue: §3" + blueTeamMembers);
                IChatComponent finalComponent1 = new ChatComponentText("");
                IChatComponent finalComponent2 = new ChatComponentText("");
                finalComponent1.appendSibling(finalComponentText1);
                finalComponent1.appendSibling(buttonRed);
                finalComponent2.appendSibling(finalComponentText2);
                finalComponent2.appendSibling(buttonBlue);

                buttonRed.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pc " + redTeamMembers));
                buttonBlue.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pc " + blueTeamMembers));

                sender.addChatMessage(finalComponent1);
                sender.addChatMessage(finalComponent2);


            }

        }


    }

    public static boolean getContainsPlayerId(int id) {
        return playerIds.containsValue(id);
    }

    public static boolean isPlayerListFull() {
        return playerIds.size() == playerAmount;
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return index == 0;
    }

    public static boolean bot(Entity en) {
        if (en.getName().equalsIgnoreCase("\\247r")) {
            return true;
        } else {
            if (en.getName().contains("\\247c")) {
                return true;
            } else {
                if (Pattern.compile("\\w{3,16}").matcher(en.getName()).matches()
                        && Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()
                        .stream()
                        .anyMatch($pl ->
                                $pl.getDisplayName() != null
                                        && $pl.getDisplayName().toString().contains(en.getName())
                        )) {
                    return false;
                } else {
                    if (en.getName().startsWith("§c")) {
                        return true;
                    } else {

                        String n = en.getDisplayName().getUnformattedText();
                        String na = en.getName();
                        if (n.contains("§") || na.contains("Grim Reaper")) {
                            return n.contains("[NPC] ");
                        } else {
                            if (n.isEmpty() && en.getName().isEmpty()) {
                                return true;
                            }

                            if (n.length() == 10) {
                                int num = 0;
                                int let = 0;
                                char[] var4 = n.toCharArray();

                                for (char c : var4) {
                                    if (Character.isLetter(c)) {
                                        if (Character.isUpperCase(c)) {
                                            return false;
                                        }

                                        ++let;
                                    } else {
                                        if (!Character.isDigit(c)) {
                                            return false;
                                        }

                                        ++num;
                                    }
                                }

                                return num >= 2 && let >= 2;
                            }
                        }
                    }
                }
            }


        }



        return false;
    }



}
