package com.addikted.immersivestream.events;

import com.addikted.immersivestream.immersiveStream;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onPlayerChat implements Listener {
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if (immersiveStream.purging) {
            if (message.toLowerCase().equals(immersiveStream.purgeWord)) {
                e.setCancelled(true);
                if (!(e.getPlayer().isOp())) {
                    immersiveStream.safePlayers.add(e.getPlayer());
                }
            }
        }
    }
}
