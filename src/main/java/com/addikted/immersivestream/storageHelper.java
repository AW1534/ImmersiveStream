package com.addikted.immersivestream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class storageHelper {
    static JavaPlugin plugin;
    public static void Initialize(JavaPlugin _plugin) {
        plugin = _plugin;
        File folder = new File(plugin.getDataFolder()+File.separator+"players");
        if(!folder.exists()) { folder.mkdirs(); }
    }

    public static enum Key {
        LOCATION_X,
        LOCATION_Y,
        LOCATION_Z,

        ROTATION_X,
        ROTATION_Y,
        ROTATION_Z,
    }

    public static void save(Player player, Key key, String value) throws IOException {
        File folder = new File(plugin.getDataFolder()+File.separator+"players");
        // FileConfiguration data = YamlConfiguration.loadConfiguration(File.join(folder, player.getName()+".yml"));
        if(!folder.exists()) { folder.mkdirs(); }

        File playerFile = new File(plugin.getDataFolder()+File.separator+"players"+File.separator+player.getName()+".yml");
        if(!playerFile.exists()) playerFile.createNewFile();


    }
    public static void load(Player player, Key key) throws IOException {
        File folder = new File(plugin.getDataFolder()+File.separator+"players");
        if(!folder.exists()) { folder.mkdirs(); }
        File playerFile = new File(plugin.getDataFolder()+File.separator+"players"+File.separator+player.getName()+".yml");
        if(!playerFile.exists()) throw new IOException();
    }
}
