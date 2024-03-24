package com.addikted.immersivestream.GUIs;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIHelper {
    public static ItemStack createItem(String name, Material mat, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        // "commit" the changes
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createPlaceholder() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1, (short) 0);
        ItemMeta meta = item.getItemMeta();
        meta.setLocalizedName("placeholder");
        meta.setDisplayName(" ");

        // "commit" the changes
        item.setItemMeta(meta);
        return item;
    }
}
