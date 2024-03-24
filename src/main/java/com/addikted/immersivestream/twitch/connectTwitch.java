package com.addikted.immersivestream.twitch;

import com.addikted.immersivestream.immersiveStream;
import com.addikted.immersivestream.GUIs.GUIHelper;
import com.addikted.immersivestream.StorageHelper;
import net.wesjd.anvilgui.AnvilGUI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import static org.bukkit.Bukkit.getServer;

public class connectTwitch implements CommandExecutor {

    PluginManager pluginManager = getServer().getPluginManager();
    Plugin plugin = pluginManager.getPlugin("immersivestream");

    public boolean connectTwitch(Player player, String name) {
        player.sendMessage("[ImmersiveStream]: connecting to twitch...");
        String id = immersiveStream.GetTwitchUser(name).getId();

        if (StringUtils.isNotEmpty(id)) {
            StorageHelper.save(player, StorageHelper.Key.TWITCH_ID, id);
            player.sendMessage(ChatColor.GREEN + "[ImmersiveStream]: Connected to Twitch!");
            player.sendMessage(ChatColor.YELLOW + "[ImmersiveStream]: To verify your account, you will receive a message on Twitch from [BOT NAME]."); //get the username of the bot

            return true;
        }

        player.sendMessage(ChatColor.RED + "[ImmersiveStream]: Could not connect to Twitch! account not found!");
        return false;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("connecttwitch")) { return true; } // halt if the command is not connecttwitch

        if (!(sender instanceof Player)) {
            sender.sendMessage("[ImmersiveStream]: This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage("test");

        AtomicReference<String> textEntered;

        if (args.length <= 0) {
            textEntered = new AtomicReference<>("");
            player.sendMessage(ChatColor.AQUA + "[ImmersiveStream]: Opening menu...");
           new AnvilGUI.Builder()
                    .onClose(stateSnapshot -> {
                        stateSnapshot.getPlayer().sendMessage("You closed the inventory.");
                    })
                    .onClick((slot, stateSnapshot) -> { // Either use sync or async variant, not both
                        if(slot != AnvilGUI.Slot.OUTPUT) {
                            return Collections.emptyList();
                        }

                        textEntered.set(stateSnapshot.getText().replaceFirst("\u200C", "")); // remove whitespace added previously

                        return Arrays.asList(AnvilGUI.ResponseAction.close());

//                        if(stateSnapshot.getText().equalsIgnoreCase("you")) {
//                            stateSnapshot.getPlayer().sendMessage("You have magical powers!");
//                            return Arrays.asList(AnvilGUI.ResponseAction.close());
//                        } else {
//                            return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("Try again"));
//                        }
                    })
                    .title("Twitch username")                                       //set the title of the GUI (only works in 1.14+)
                    .itemLeft(GUIHelper.createItem("\u200C", Material.PAPER, Collections.singletonList("Rename to connect your Twitch account")))
                    .plugin(plugin)                                                    //set the plugin instance
                    .open(player);                                                     //opens the GUI for the player provided
        } else if (args.length == 1) {
            textEntered = new AtomicReference<>("");
            textEntered.set(args[0]);
        } else {
            textEntered = new AtomicReference<>("");
        }

        return connectTwitch(player, textEntered.get());
    }

    int CONNECTION_FAIL = -1;
    int CONNECTION_SUCCESS = 1;

    public int connectAccount() {
        return 0;
    }
}
