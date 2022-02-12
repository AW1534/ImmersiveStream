package com.addikted.immersivestream.commands;

import com.addikted.immersivestream.GUIs.menuGUI;
import com.addikted.immersivestream.immersiveStream;
import com.sun.deploy.util.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static com.addikted.immersivestream.immersiveStream.purgeWord;
import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class purge implements CommandExecutor {
    Random random = new Random();

    Server server = getServer();
    PluginManager pluginManager = server.getPluginManager();
    ConsoleCommandSender consoleSender = server.getConsoleSender();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            //return true;
        }

        // check if user has operator permissions
        if (!(sender.isOp())) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Immersive Stream]: You do not have permission to use this command :(");
            return true;
        }


        int threshold;
        try {
            threshold = args[0] != null ? Integer.parseInt(args[0]) : 1;
        } catch(ArrayIndexOutOfBoundsException e) {
            threshold = 1;
        }

        // send a message to all players saying they are being purged
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The server is currently being purged. look out!");
        }

        immersiveStream.purging = true;
        immersiveStream.purgeCount = 0;
        immersiveStream.safePlayers = new ArrayList<>();

        // wait 2 seconds
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(immersiveStream.class), new Runnable() {
            public void run() {
                // list of letter words
                List<String> words = Arrays.asList("Hi", "Immersive Stream", "Rap God", "2 + 2 = 5", "This is a purge", "bye yall", "dont kick me pls");
                String randomElement = words.get(random.nextInt(words.size()));
                purgeWord = randomElement.toLowerCase();

                for (Player p : getServer().getOnlinePlayers()) {
                    p.sendMessage(ChatColor.GOLD + "Please type " + ChatColor.RED + ChatColor.BOLD + randomElement + ChatColor.GOLD + " in chat to avoid being purged!");
                }
            }
        }, (random.nextInt(10) * 20)); // Always multiply by twenty because that's the amount of ticks in Minecraft

        if (threshold == 1) {
            sender.sendMessage("Purging...");
        }
        else {
            sender.sendMessage("Purging " + args[1] + " players...");
        }

        // wait 10 seconds
        int finalThreshold = threshold;

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(immersiveStream.class), new Runnable() {
            public void run() {
                int threshold = finalThreshold;
                if (immersiveStream.purgeCount < threshold) {
                    for(Player p : getServer().getOnlinePlayers()) {
                        if (immersiveStream.purgeCount >= threshold) break;
                        if (!immersiveStream.safePlayers.contains(p)) {
                            if (!p.isOp()) {
                                p.kickPlayer("" + ChatColor.RED + ChatColor.BOLD + "The server is currently being purged.\nunfortunately you were not able to type " + ChatColor.GOLD + ChatColor.BOLD + purgeWord + ChatColor.RED + ChatColor.BOLD + " in time.\nplease try again later.");
                                immersiveStream.purgeCount++;
                            }
                        }
                    }
                }

                // if the purge count is less than the threshold, then start kicking random non-safe whitelisted players
                if (immersiveStream.purgeCount < threshold) {
                    for(Player p : getServer().getOnlinePlayers()) {
                        if (immersiveStream.purgeCount >= threshold) break;
                        if (!p.isOp() && !p.isWhitelisted() && !immersiveStream.safePlayers.contains(p)) {
                            if (!p.isOp()) {
                                p.kickPlayer("" + ChatColor.RED + ChatColor.BOLD + "The server is currently being purged.\nunfortunately you were randomly chosen to be purged.\nplease try again later.");
                                immersiveStream.purgeCount++;
                            }
                        }
                    }
                }

                // if the purge count is less than the threshold, then start kicking random whitelisted players, safe or not
                if (immersiveStream.purgeCount < threshold) {
                    for(Player p : getServer().getOnlinePlayers()) {
                        if (immersiveStream.purgeCount >= threshold) break;
                        if (!p.isOp() && !p.isWhitelisted()) {
                            p.kickPlayer("" + ChatColor.RED + ChatColor.BOLD + "The server is currently being purged.\nunfortunately you were randomly chosen to be purged.\nplease try again later.");
                            immersiveStream.purgeCount++;
                        }
                    }
                }

                // if the purge count is still less than the threshold, then start kicking random players no matter what. no mercy.
                if (immersiveStream.purgeCount < threshold) {
                    for(Player p : getServer().getOnlinePlayers()) {
                        if (immersiveStream.purgeCount >= threshold) break;
                        if (!(p == sender)) {
                            p.kickPlayer("" + ChatColor.RED + ChatColor.BOLD + "The server is currently being purged.\nunfortunately you were randomly chosen to be purged.\nplease try again later.");
                            getServer().getConsoleSender().sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Player " + p.getName() + " was purged.");
                            immersiveStream.purgeCount++;
                        }
                    }
                }

                // by now,
                sender.sendMessage(ChatColor.GOLD + "Purged " + immersiveStream.purgeCount + " players!");
                immersiveStream.purging = false;
                immersiveStream.purgeCount = 0;
                immersiveStream.safePlayers = new ArrayList<>();
            }
        }, (10 * 20)); // Always multiply by twenty because that's the amount of ticks in Minecraft



        return true;
    }
}
