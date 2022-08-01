package me.lopataa.grayowl.minecraftListeners;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import me.lopataa.grayowl.Grayowl;
import org.bukkit.ChatColor;

public class onMessageListener {
    GatewayDiscordClient client;
    Grayowl plugin;

    public onMessageListener(GatewayDiscordClient client_arg, Grayowl plugin_arg) {
        client = client_arg;
        plugin = plugin_arg;

        client.getEventDispatcher().on(MessageCreateEvent.class).subscribe(event -> {
            if (event.getMessage().getChannelId().equals(Snowflake.of(plugin.getConfig().getString("chat.channel")))) {
                if(event.getMessage().getAuthor().get().isBot()) {
                    return;
                }
                plugin.getServer().broadcastMessage("[" + net.md_5.bungee.api.ChatColor.of("#5865F2") + "Discord" + ChatColor.RESET + "] " +"<" + event.getMessage().getAuthor().get().getUsername() + "> "+ event.getMessage().getContent());
            }

        });
    }

}
