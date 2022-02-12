package com.addikted.immersivestream;

import com.addikted.immersivestream.commands.menu;
import com.addikted.immersivestream.commands.purge;
import com.addikted.immersivestream.events.*;
import com.addikted.immersivestream.twitch.connectTwitch;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class immersiveStream extends JavaPlugin {
    public static boolean purging = false;
    public static int purgeCount;
    public static List<Player> safePlayers;
    public static String purgeWord;

    Server server = getServer();
    PluginManager pluginManager = server.getPluginManager();
    ConsoleCommandSender consoleSender = server.getConsoleSender();

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 14286);

        storageHelper.Initialize(JavaPlugin.getPlugin(immersiveStream.class)); // << storage class is disabled due to issues lmao

        // test storage class

        // Initialize events
        pluginManager.registerEvents(new onPlayerJoin(), this);
        pluginManager.registerEvents(new onPlayerQuit(), this);
        pluginManager.registerEvents(new onInventoryClick(), this);
        pluginManager.registerEvents(new onWhitelist(), this);
        pluginManager.registerEvents(new onPlayerChat(), this);

        // Initialize commands
        getCommand("menu").setExecutor(new menu());
        getCommand("connecttwitch").setExecutor(new connectTwitch());
        getCommand("purge").setExecutor(new purge());

        consoleSender.sendMessage(ChatColor.GREEN + "[Immersive Stream]: Plugin has been initialized");
        if (server.hasWhitelist()) {
            consoleSender.sendMessage("§c§l[Immersive Stream]: please set white-list to false in server.properties for Immersive Stream to operate correctly.");
        } else {
            consoleSender.sendMessage("§a§l[Immersive Stream]: Whitelisted players will be allowed to play.");
        }
    }

    @Override
    public void onDisable() {
        consoleSender.sendMessage(ChatColor.RED + "[Immersive Stream]: Plugin has been disabled");
    }
}
