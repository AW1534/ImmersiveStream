package com.addikted.immersivestream;

import org.bukkit.OfflinePlayer;

import static org.bukkit.Bukkit.getWhitelistedPlayers;

public class generalHelper {

    public static boolean isWhitelisted(OfflinePlayer player) {
        if  (getWhitelistedPlayers().contains(player)) {
            return true;
        } else {
            return false;
        }
    }
}
