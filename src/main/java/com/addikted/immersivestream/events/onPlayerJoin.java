package com.addikted.immersivestream.events;

import com.addikted.immersivestream.StorageHelper;
import com.addikted.immersivestream.generalHelper;
import com.addikted.immersivestream.immersiveStream;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.addikted.immersivestream.immersiveStream.*;
import static org.bukkit.Bukkit.*;

public class onPlayerJoin implements Listener {

    public static void requestOAuth(Player player) {
        if (StringUtils.isBlank(token)) {
            Server server = getServer();

            String authURL = "https://id.twitch.tv/oauth2/authorize?response_type=code" +
                    "&client_id=" + config.getString("client_id") +
                    "&redirect_uri=https://twitchtokengenerator.com" +
                    "&scope=analytics:read:extensions+user:edit+user:read:email+clips:edit+bits:read+analytics:read:games+user:edit:broadcast+user:read:broadcast+chat:read+chat:edit+channel:moderate+channel:read:subscriptions+whispers:read+whispers:edit+moderation:read+channel:read:redemptions+channel:edit:commercial+channel:read:hype_train+channel:read:stream_key+channel:manage:extensions+channel:manage:broadcast+user:edit:follows+channel:manage:redemptions+channel:read:editors+channel:manage:videos+user:read:blocked_users+user:manage:blocked_users+user:read:subscriptions+user:read:follows+channel:manage:polls+channel:manage:predictions+channel:read:polls+channel:read:predictions+moderator:manage:automod+channel:manage:schedule+channel:read:goals+moderator:read:automod_settings+moderator:manage:automod_settings+moderator:manage:banned_users+moderator:read:blocked_terms+moderator:manage:blocked_terms+moderator:read:chat_settings+moderator:manage:chat_settings+channel:manage:raids+moderator:manage:announcements+moderator:manage:chat_messages+user:manage:chat_color+channel:manage:moderators+channel:read:vips+channel:manage:vips+user:manage:whispers+channel:read:charity+moderator:read:chatters+moderator:read:shield_mode+moderator:manage:shield_mode+moderator:read:shoutouts+moderator:manage:shoutouts+moderator:read:followers+channel:read:guest_star+channel:manage:guest_star+moderator:read:guest_star+moderator:manage:guest_star+channel:bot+user:bot+user:read:chat+channel:manage:ads+channel:read:ads+user:read:moderated_channels+user:write:chat" +
                    "&state=frontend|QmxKWGs0bTlYM1BLKytJcnFQQmY3dz09&force_verify=true";

            player.sendMessage(
                    "§4§o[Immersive Stream]: No OAuth token found in config.yml!\n" +
                            "[Immersive Stream]: Click the link below to get your OAuth token, and add it to the ImmersiveStream config file in the plugins folder."
            );
            server.dispatchCommand( // https://www.minecraftjson.com/
                    server.getConsoleSender(),
                    "/tellraw " + player.getName() +
                            "{\"text\":\"Click here to get OAuth token\",\"bold\":true,\"underlined\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"" + authURL + "\",\"value\":\"[URL]\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":[{\"text\":\"" + authURL + "\",\"italic\":true}]}}");
        }
    }
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (immersiveStream.purging) {
            player.kickPlayer(ChatColor.RED + "The server is currently being purged.\nPlease try again later.");
            e.setJoinMessage(null);
            return;
        }

        player.sendMessage("heuheu - " + StorageHelper.playerFromTwitchId("232597344").getName());

        if(!player.hasPlayedBefore()) {
            StorageHelper.savePlayerPosition(player);

            if (player.isOp()) {
                requestOAuth(player);
            }
        }

        if  (!(generalHelper.isWhitelisted(getOfflinePlayer(player.getName())))) {
            player.setGameMode(GameMode.SPECTATOR);
            e.setJoinMessage(null);
        } else {
            player.setGameMode(getDefaultGameMode());
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[Immersive Stream]: Welcome to the server! use /whitelist to add players to the game!");
        }

        if(hasWhitelist()){
            player.sendMessage("§c§l(!) IMPORTANT: please set white-list to false in server.properties for Immersive Stream to operate correctly.");
        }
    }
}
