package com.addikted.immersivestream.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.Arrays;
import java.util.List;

public class onTabComplete implements Listener {
    // TODO: fix this mess
    String[] aliases_ = {
            "gui",
            "immersivestream",
            "imms",
            "immersivestream:gui",
            "immersivestream:immersivestream",
            "immersivestream:imms",
    };
    List<String> aliases = Arrays.asList(aliases_);

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        List<String > completions = event.getCompletions();
        completions.removeIf(s -> aliases.contains(s));

        event.setCompletions(completions);

    }
}
