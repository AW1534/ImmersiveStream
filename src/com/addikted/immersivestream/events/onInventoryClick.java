package com.addikted.immersivestream.events;

import com.addikted.immersivestream.GUIs.ConnectAccountGUI;
import com.addikted.immersivestream.GUIs.menuGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class onInventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof menuGUI) {
            menuGUI(e);
        } else if (e.getClickedInventory().getHolder() instanceof ConnectAccountGUI) {

        }
    }

    public void menuGUI(InventoryClickEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) { return; }
        player.sendMessage(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getLocalizedName());
        if (e.getCurrentItem().getType() == Material.PAPER) {
            player.performCommand("connecttwitch");
        }
    }

}
