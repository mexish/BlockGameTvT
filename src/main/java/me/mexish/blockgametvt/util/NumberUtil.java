package me.mexish.blockgametvt.util;

import me.mexish.blockgametvt.command.impl.RandomTeamCommand;

import java.util.Random;


/**
 * @author mexish
 * */
public class NumberUtil {

    public static int generateRandomPlayerId(int limit) {
        int randomPlayerId = (int) ((Math.random() * (limit - 1)) + 1);
        while (RandomTeamCommand.getContainsPlayerId(randomPlayerId)) {
            randomPlayerId = (int) ((Math.random() * (limit - 1)) + 1);
        }


        return randomPlayerId;
    }


}
