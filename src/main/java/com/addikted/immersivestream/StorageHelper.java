package com.addikted.immersivestream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//TODO: THis class is probably very unsafe. do some testing.

public class StorageHelper {
    private static immersiveStream plugin;
    private static Server server;
    static File folder;

    public static void initialize(immersiveStream _plugin) {
        plugin = _plugin;
        server = plugin.server;

        folder = new File(plugin.getDataFolder() + File.separator + "players");
        if (!folder.exists()) {
            folder.mkdirs(); // This seems like it needs some testing
        }
    }

    public enum Key {
        LAST_WORLD,
        LOCATION_X,
        LOCATION_Y,
        LOCATION_Z,
        ROTATION_YAW,
        ROTATION_PITCH,
        TWITCH_ID
    }

    public static void save(Player player, Key key, int value) {save(player, key, String.valueOf(value));} // allows integer keys
    public static void save(Player player, Key key, float value) {save(player, key, String.valueOf(value));} // allows float keys
    public static void save(Player player, Key key, double value) {save(player, key, String.valueOf(value));} // allows double keys
    public static void save(Player player, Key key, String value) {
        if (value == null || value.isEmpty()) {
            // If the value is null or empty, remove the key from the player's data
            removeKey(player, key);
            return;
        }

        try {
            File playerFile = getPlayerFile(player);
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
            playerConfig.set(key.toString(), value);
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void savePlayerPosition(Player player) {
        Location location = player.getLocation();

        save(player, Key.LAST_WORLD, location.getWorld().getName());
        save(player, Key.LOCATION_X, location.getX());
        save(player, Key.LOCATION_Y, location.getY());
        save(player, Key.LOCATION_Z, location.getZ());
        save(player, Key.ROTATION_YAW, location.getYaw());
        save(player, Key.ROTATION_PITCH, location.getPitch());
    }

    public static void saveAllPlayers() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if (!generalHelper.isWhitelisted(p)) {return;}

            savePlayerPosition(p);
        }
    }

    public static void backToLastLocation(Player player) {

        String world = load(player, Key.LAST_WORLD);
        double x = Double.parseDouble(load(player, Key.LOCATION_X));
        double y = Double.parseDouble(load(player, Key.LOCATION_Y));
        double z = Double.parseDouble(load(player, Key.LOCATION_Z));
        float yaw = Float.parseFloat(load(player, Key.ROTATION_YAW));
        float pitch = Float.parseFloat(load(player, Key.ROTATION_PITCH));

        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        player.teleport(location);
    }

    public static String load(Player player, Key key) {
        File playerFile = getPlayerFile(player);

        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        // If the value for the key is not present, return null
        return playerConfig.getString(key.toString());
    }

    private static File getPlayerFile(Player player) {
        return new File(plugin.getDataFolder() + File.separator + "players" + File.separator + player.getUniqueId() + ".yml");
    }

    public static File findPlayerFileFromUniqueValue(Key key, String value) {
        for(File f : folder.listFiles()) {
            System.out.println(f);
            if (YamlConfiguration.loadConfiguration(f).getString(key.toString()) == value) {
                return f;
            }
        }

        return null;
    }

    public static Player playerFromTwitchId(String id) {
        return server.getPlayer(UUID.fromString(findPlayerFileFromUniqueValue(Key.TWITCH_ID, id).getName()));
    }

    public static YamlConfiguration findPlayerFromUniqueValue(Key key, String value) {
        for(File f : folder.listFiles()) {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
            if (conf.getString(key.toString()) == value) {
                return conf;
            }
        }

        return null;
    }


    private static void removeKey(Player player, Key key) {
        File playerFile = getPlayerFile(player);
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.set(key.toString(), null); // Remove the key from the configuration
        try {
            playerConfig.save(playerFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
