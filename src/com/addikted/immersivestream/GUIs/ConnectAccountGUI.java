package com.addikted.immersivestream.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Collections;

public class ConnectAccountGUI implements InventoryHolder {
    private final Inventory inv;

    public ConnectAccountGUI() {
        inv = Bukkit.createInventory(this, InventoryType.ANVIL, "Connect twitch");
        init();
    }

    private void init() {
        inv.setItem(0, GUIHelper.createItem("username", Material.PAPER, Collections.singletonList("Rename to connect your Twitch account")));
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }
}
