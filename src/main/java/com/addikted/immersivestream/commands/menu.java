package com.addikted.immersivestream.commands;

import com.addikted.immersivestream.GUIs.menuGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class menu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("menu")) { return true; } // halt if the command is not menu

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;

        menuGUI gui = new menuGUI();
        player.openInventory(gui.getInventory());
        player.sendMessage(ChatColor.AQUA + "Opening menu...");
        return true;
    }
}
