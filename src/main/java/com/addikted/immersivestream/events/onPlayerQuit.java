package com.addikted.immersivestream.events;

import com.addikted.immersivestream.generalHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class onPlayerQuit implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!generalHelper.isWhitelisted(getOfflinePlayer(event.getPlayer().getName()))) {
            event.setQuitMessage(null);
        }
    }
}
