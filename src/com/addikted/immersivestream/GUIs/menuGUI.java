package com.addikted.immersivestream.GUIs;

import com.addikted.immersivestream.GUIs.GUIHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class menuGUI implements InventoryHolder {
    private Inventory inv;

    public menuGUI() {
        inv = Bukkit.createInventory(this, 54, "Menu");
        init();
    }

    private void init() {
        ItemStack twitch = GUIHelper.createItem(ChatColor.LIGHT_PURPLE + "Connect Twitch", Material.PAPER, Collections.singletonList("§7Connect to Twitch to enable extra features"));
        twitch.getItemMeta().setLocalizedName("menu:connect_twitch");
        inv.setItem(13, twitch);
        ItemStack placeholder = GUIHelper.createPlaceholder();
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, placeholder);
            }
        }
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }
}
