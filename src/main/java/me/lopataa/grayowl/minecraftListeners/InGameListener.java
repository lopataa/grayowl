package me.lopataa.grayowl.minecraftListeners;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;
import me.croabeast.advancementinfo.AdvancementInfo;
import me.lopataa.grayowl.Grayowl;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InGameListener implements Listener {
    GatewayDiscordClient client;
    Grayowl plugin;

    public InGameListener(GatewayDiscordClient client_arg, Grayowl grayowl) {
        client = client_arg;
        plugin = grayowl;
    }

    @EventHandler
    public boolean onChat(AsyncPlayerChatEvent event) {
        if (plugin.getConfig().getBoolean("chat.messages.chat") == false) {
            return true;
        }
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of(plugin.getConfig().getString("chat.channel"))).block();
        channel.getRestChannel().createMessage("<"+event.getPlayer().getName() + "> " + event.getMessage()).subscribe();
        return true;
    }

    @EventHandler
    public boolean onDeath(PlayerDeathEvent event) {
        if (plugin.getConfig().getBoolean("chat.messages.death") == false) {
            return true;
        }
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of(plugin.getConfig().getString("chat.channel"))).block();
        channel.getRestChannel().createMessage(event.getDeathMessage()).subscribe();
        return true;
    }

    @EventHandler
    public boolean onConnect(PlayerJoinEvent event) {
        if (plugin.getConfig().getBoolean("chat.messages.join") == false) {
            return true;
        }
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of(plugin.getConfig().getString("chat.channel"))).block();
        channel.getRestChannel().createMessage(event.getPlayer().getName() + " has joined the game").subscribe();
        return true;
    }

    @EventHandler
    public boolean onDisconnect(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("chat.messages.leave") == false) {
            return true;
        }
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of(plugin.getConfig().getString("chat.channel"))).block();
        channel.getRestChannel().createMessage(event.getPlayer().getName() + " left the game").subscribe();
        return true;
    }

    @EventHandler
    public boolean onAdvancement(PlayerAdvancementDoneEvent event) {
        if (plugin.getConfig().getBoolean("chat.messages.advancement") == false) {
            return true;
        }
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of(plugin.getConfig().getString("chat.channel"))).block();
        Advancement advancement = Bukkit.getAdvancement(event.getAdvancement().getKey());
        String advancementTitle = new AdvancementInfo(advancement).getTitle();
        if (advancementTitle == null || advancementTitle.isEmpty()) {
            return false;
        }
        channel.getRestChannel().createMessage(event.getPlayer().getName() + " has completed advancement [" + advancementTitle + "]").subscribe();
        return true;
    }
}
