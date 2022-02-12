package com.addikted.immersivestream.events;

import com.addikted.immersivestream.generalHelper;
import com.addikted.immersivestream.immersiveStream;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class onPlayerQuit implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (!generalHelper.isWhitelisted(getOfflinePlayer(e.getPlayer().getName()))) {
            e.setQuitMessage(null);
            if (immersiveStream.purging) {
                immersiveStream.purgeCount++;
                e.setQuitMessage("" + ChatColor.RED + ChatColor.BOLD + "Player " + e.getPlayer().getName() + " was purged.");
            }
        }
    }
}
