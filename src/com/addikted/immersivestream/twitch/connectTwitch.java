package com.addikted.immersivestream.twitch;


import com.addikted.immersivestream.GUIs.ConnectAccountGUI;
import com.addikted.immersivestream.twitch.Connect;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class connectTwitch implements CommandExecutor {
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage("test");
        if (!cmd.getName().equalsIgnoreCase("connecttwitch")) { return true; } // halt if the command is not connecttwitch

        if (args.length <= 0) {
            player.sendMessage(ChatColor.AQUA + "Opening menu...");
            player.openInventory(new ConnectAccountGUI().getInventory());
        } else if (args.length == 1) {
            player.sendMessage("connecting to twitch...");
            if (Connect.Test(args[0]) == true) {
                player.sendMessage(ChatColor.GREEN + "Connected to Twitch!");
            } else {
                player.sendMessage(ChatColor.RED + "Could not connect to Twitch! account not found!");
            }

        }

        return true;
    }
}
