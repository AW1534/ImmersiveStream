package com.addikted.immersivestream.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Collections;

public class ConnectAccountGUI implements InventoryHolder {
    private AnvilInventory inv;

    public ConnectAccountGUI() {
        inv = (AnvilInventory) Bukkit.createInventory(this, InventoryType.ANVIL, "Menu");
        init();
    }

    private void init() {
        inv.setItem(0, GUIHelper.createItem("Rename this to your twitch account's username", Material.PAPER, Collections.singletonList("Click to rename")));
    }


    @Override
    public AnvilInventory getInventory() {
        return inv;
    }
}
