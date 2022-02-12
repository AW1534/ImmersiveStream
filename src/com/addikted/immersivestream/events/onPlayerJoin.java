package com.addikted.immersivestream.events;

import com.addikted.immersivestream.generalHelper;
import com.addikted.immersivestream.immersiveStream;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.Bukkit.*;

public class onPlayerJoin implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (immersiveStream.purging) {
            player.kickPlayer(ChatColor.RED + "The server is currently being purged.\nPlease try again later.");
            e.setJoinMessage(null);
            return;
        }

        if  (!(generalHelper.isWhitelisted(getOfflinePlayer(player.getName())))) {
            player.setGameMode(GameMode.SPECTATOR);
            e.setJoinMessage(null);
        } else {
            player.setGameMode(getDefaultGameMode());
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[Immersive Stream]: Welcome to the server! use /whitelist to add players to the game!");
        }

        if(hasWhitelist()){
            player.sendMessage("§c§l(!) IMPORTANT: please set white-list to false in server.properties for Immersive Stream to operate correctly.");
        }
    }
}
