package com.addikted.immersivestream.events;

import com.addikted.immersivestream.generalHelper;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.Bukkit.*;

public class onPlayerJoin implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if  (!(generalHelper.isWhitelisted(getOfflinePlayer(player.getName())))) {
            player.setGameMode(GameMode.SPECTATOR);
            event.setJoinMessage(null);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome to the server! /help for info");
        } else {
            player.setGameMode(getDefaultGameMode());
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome to the server! use /whitelist to add players to the game!");
        }

        if(hasWhitelist()){
            player.sendMessage("§c§l(!) IMPORTANT: please set white-list to false in server.properties for Immersive Stream to operate correctly.");
        }
    }
}
