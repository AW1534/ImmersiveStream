package com.addikted.immersivestream.events;

import com.addikted.immersivestream.generalHelper;
import com.addikted.immersivestream.immersiveStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.bukkit.Bukkit.*;

public class onWhitelist implements Listener {
    @EventHandler
    public void onWhiteList(PlayerCommandPreprocessEvent e) throws InterruptedException {
        String message = e.getMessage().toLowerCase(Locale.ROOT);
        // if someone is removed from the whitelist, they will be set to spectator
        if (message.contains("/whitelist remove") && e.getPlayer().isOp()) {
            // if the player is not whitelisted, execute the code silently
            Player target = getPlayer(message.split(" ", -1)[2]);
            if (!generalHelper.isWhitelisted(target)) {
                target.setGameMode(GameMode.SPECTATOR);
                wait(5);
                if (!generalHelper.isWhitelisted(target)) {
                    target.setGameMode(getDefaultGameMode());
                }
            } else { // otherwise, execute the code normally
                target.setGameMode(GameMode.SPECTATOR);
                target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                target.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "You have been removed from the whitelist.");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(immersiveStream.class), new Runnable() {
                    public void run() {
                        if (!generalHelper.isWhitelisted(target)) {
                            target.setGameMode(getDefaultGameMode());
                            target.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "(!) Due to an issue with a security feature, you were just temporarily set to spectator. if this persists, please contact the plugin developer to resolve this issue.");
                        }
                    }
                }, (50));

            }

        }
        // if someone is added to the whitelist, they will be prompted to rejoin the server
        if (message.contains("/whitelist add") && e.getPlayer().isOp()) {
            Player target = getPlayer(message.split(" ", -1)[2]);
            target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1, 1);
            target.sendMessage(ChatColor.GREEN + "(!) You have been Added to the whitelist! rejoin to participate in the server.");
        }
        // if the whitelist is reloaded, all players who are not whitelisted will be set to spectator
        if (message.contains("/whitelist reload") && e.getPlayer().isOp()) {
            getConsoleSender().sendMessage(ChatColor.GREEN + "(!) Whitelist reloaded.");
            getOnlinePlayers().forEach(player -> {
                if (!generalHelper.isWhitelisted(player)) {
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, 1);
                        player.sendMessage(ChatColor.GREEN + "(!) Due to an issue with a manual whitelist reload, Immersive Stream has detected that you are not whitelisted, and you have been set to spectator. if this is not the case, please contact the plugin developer to resolve this issue.");
                    }
                }
            });
        }
    }
}