package com.addikted.immersivestream.twitch;

import com.addikted.immersivestream.GUIs.ConnectAccountGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class connectTwitch implements CommandExecutor {
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage("test");
        if (!cmd.getName().equalsIgnoreCase("connecttwitch")) { return true; } // halt if the command is not connecttwitch
        player.openInventory(new ConnectAccountGUI().getInventory());


        if (args.length == 0) {

        } else if (args.length == 1) {
            // connect to twitch with the given username
        }

        return true;
    }
}
