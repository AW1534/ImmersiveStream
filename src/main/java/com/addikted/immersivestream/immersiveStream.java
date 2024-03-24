package com.addikted.immersivestream;

import com.addikted.immersivestream.commands.menu;
import com.addikted.immersivestream.commands.purge;
import com.addikted.immersivestream.events.*;
import com.addikted.immersivestream.twitch.TwitchEventHandler;
import com.addikted.immersivestream.twitch.connectTwitch;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.ITwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.common.util.ThreadUtils;
import com.github.twitch4j.helix.domain.User;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class immersiveStream extends JavaPlugin {
    public static boolean purging = false;
    public static int purgeCount;
    public static List<Player> safePlayers;
    public static String purgeWord;
    public static ITwitchClient twitchClient = null;
    public static String token;
    private static OAuth2Credential credential = null;

    Server server = getServer();
    PluginManager pluginManager = server.getPluginManager();
    public static FileConfiguration config;
    ConsoleCommandSender consoleSender = server.getConsoleSender();
    ScheduledThreadPoolExecutor exec;

    public static User GetTwitchUser(String name) {
        return twitchClient.getHelix().getUsers(token, null, Arrays.asList(name))
                .execute().getUsers().get(0);
    }

    private void buildTwitchClient() {
        loadConfig();
        twitchClientClose();
        generateOAuthCredential();
        twitchClientSetup();
    }
// https://id.twitch.tv/oauth2/authorize?response_type=code&client_id=dfge3smzih2afjds3yanu84onraqkj&redirect_uri=https://twitchtokengenerator.com&scope=analytics:read:extensions+user:edit+user:read:email+clips:edit+bits:read+analytics:read:games+user:edit:broadcast+user:read:broadcast+chat:read+chat:edit+channel:moderate+channel:read:subscriptions+whispers:read+whispers:edit+moderation:read+channel:read:redemptions+channel:edit:commercial+channel:read:hype_train+channel:read:stream_key+channel:manage:extensions+channel:manage:broadcast+user:edit:follows+channel:manage:redemptions+channel:read:editors+channel:manage:videos+user:read:blocked_users+user:manage:blocked_users+user:read:subscriptions+user:read:follows+channel:manage:polls+channel:manage:predictions+channel:read:polls+channel:read:predictions+moderator:manage:automod+channel:manage:schedule+channel:read:goals+moderator:read:automod_settings+moderator:manage:automod_settings+moderator:manage:banned_users+moderator:read:blocked_terms+moderator:manage:blocked_terms+moderator:read:chat_settings+moderator:manage:chat_settings+channel:manage:raids+moderator:manage:announcements+moderator:manage:chat_messages+user:manage:chat_color+channel:manage:moderators+channel:read:vips+channel:manage:vips+user:manage:whispers+channel:read:charity+moderator:read:chatters+moderator:read:shield_mode+moderator:manage:shield_mode+moderator:read:shoutouts+moderator:manage:shoutouts+moderator:read:followers+channel:read:guest_star+channel:manage:guest_star+moderator:read:guest_star+moderator:manage:guest_star+channel:bot+user:bot+user:read:chat+channel:manage:ads+channel:read:ads+user:read:moderated_channels+user:write:chat&state=frontend|QmxKWGs0bTlYM1BLKytJcnFQQmY3dz09&force_verify=true

    private void generateOAuthCredential() {    // TODO: make an intuitive way for users to create and enter an OAuth token
        // Build credential when possible
        token = config.getString("oauth_token");

        if (StringUtils.isBlank(token)) {
            String authURL = "https://id.twitch.tv/oauth2/authorize?response_type=code" +
                    "&client_id="+config.getString("client_id")+
                    "&redirect_uri=https://twitchtokengenerator.com" +
                    "&scope=analytics:read:extensions+user:edit+user:read:email+clips:edit+bits:read+analytics:read:games+user:edit:broadcast+user:read:broadcast+chat:read+chat:edit+channel:moderate+channel:read:subscriptions+whispers:read+whispers:edit+moderation:read+channel:read:redemptions+channel:edit:commercial+channel:read:hype_train+channel:read:stream_key+channel:manage:extensions+channel:manage:broadcast+user:edit:follows+channel:manage:redemptions+channel:read:editors+channel:manage:videos+user:read:blocked_users+user:manage:blocked_users+user:read:subscriptions+user:read:follows+channel:manage:polls+channel:manage:predictions+channel:read:polls+channel:read:predictions+moderator:manage:automod+channel:manage:schedule+channel:read:goals+moderator:read:automod_settings+moderator:manage:automod_settings+moderator:manage:banned_users+moderator:read:blocked_terms+moderator:manage:blocked_terms+moderator:read:chat_settings+moderator:manage:chat_settings+channel:manage:raids+moderator:manage:announcements+moderator:manage:chat_messages+user:manage:chat_color+channel:manage:moderators+channel:read:vips+channel:manage:vips+user:manage:whispers+channel:read:charity+moderator:read:chatters+moderator:read:shield_mode+moderator:manage:shield_mode+moderator:read:shoutouts+moderator:manage:shoutouts+moderator:read:followers+channel:read:guest_star+channel:manage:guest_star+moderator:read:guest_star+moderator:manage:guest_star+channel:bot+user:bot+user:read:chat+channel:manage:ads+channel:read:ads+user:read:moderated_channels+user:write:chat" +
                    "&state=frontend|QmxKWGs0bTlYM1BLKytJcnFQQmY3dz09&force_verify=true";
            for(Player player : server.getOnlinePlayers()) {
                if(player.isOp()) {

                    player.sendMessage(
                            "§4§o[Immersive Stream]: No OAuth token found in config.yml!\n" +
                                    "[Immersive Stream]: Click the link below to get your OAuth token, and add it to the ImmersiveStream config file in the plugins folder."
                    );
                    server.dispatchCommand( // https://www.minecraftjson.com/
                            consoleSender,
                            "/tellraw " + player.getName() +
                                    "{\"text\":\"Click here to get OAuth token\",\"bold\":true,\"underlined\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\""+ authURL+ "\",\"value\":\"[URL]\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":[{\"text\":\""+authURL+"\",\"italic\":true}]}}");
                }
            }

            return;
        }

        credential = new OAuth2Credential("twitch", token);
    }

    private void twitchClientSetup() {

         exec = ThreadUtils.getDefaultScheduledThreadPoolExecutor("twitch4j", Runtime.getRuntime().availableProcessors());


        // Build TwitchClient
        twitchClient = TwitchClientBuilder.builder()
                .withClientId(config.getString("client_id"))
                .withClientSecret(config.getString("client_secret"))
                .withEnableChat(true)
                .withChatAccount(credential)
                .withEnableHelix(true)
                .withDefaultAuthToken(credential)
                .withChatCommandsViaHelix(false)
                .build();

        // Join the twitch chats of these channels and enable stream/follow events
        List<String> channels = config.getStringList("channels");
        if (!channels.isEmpty()) {
            channels.forEach(name -> twitchClient.getChat().joinChannel(name));
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> server.broadcastMessage(channels.get(0)));
            twitchClient.getClientHelper().enableStreamEventListener(channels);
            twitchClient.getClientHelper().enableFollowEventListener(channels);
        }

        // Register event listeners
        twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new TwitchEventHandler(this));
    }

    private void twitchClientClose() {
        if (twitchClient != null) {
            twitchClient.getEventManager().close();
            twitchClient.close();
            twitchClient = null;
        }
    }

    private void loadConfig() {
        this.saveDefaultConfig(); // if the config file doesn't exist, create it and set defaults
        config = getConfig();
    }

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 14286);

        loadConfig();

        StorageHelper.initialize(JavaPlugin.getPlugin(immersiveStream.class));

        buildTwitchClient();


        // Initialize events
        pluginManager.registerEvents(new onPlayerJoin(), this);
        pluginManager.registerEvents(new onPlayerQuit(), this);
        pluginManager.registerEvents(new onInventoryClick(), this);
        pluginManager.registerEvents(new onWhitelist(), this);
        pluginManager.registerEvents(new onPlayerChat(), this);

        // Initialize commands
        getCommand("menu").setExecutor(new menu());
        getCommand("connecttwitch").setExecutor(new connectTwitch());
        getCommand("purge").setExecutor(new purge());

        consoleSender.sendMessage(ChatColor.GREEN + "[Immersive Stream]: Plugin has been Enabled");
        if (server.hasWhitelist()) {
            consoleSender.sendMessage("§c§l[Immersive Stream]: please set white-list to false in server.properties for Immersive Stream to operate correctly.");
        } else {
            consoleSender.sendMessage("§a§l[Immersive Stream]: Whitelisted players will be allowed to play.");
        }
    }

    @Override
    public void onDisable() {
        StorageHelper.saveAllPlayers();
        twitchClientClose();
        consoleSender.sendMessage(ChatColor.RED + "[Immersive Stream]: Plugin has been disabled");
    }
}
