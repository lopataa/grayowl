package me.lopataa.grayowl.listeners;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;
import me.croabeast.advancementinfo.AdvancementInfo;
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

    public InGameListener(GatewayDiscordClient client_arg) {
        client = client_arg;

    }

    @EventHandler
    public boolean onChat(AsyncPlayerChatEvent event) {
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of("997551922800246886")).block();
        channel.getRestChannel().createMessage("<"+event.getPlayer().getName() + "> " + event.getMessage()).subscribe();
        return true;
    }

    @EventHandler
    public boolean onDeath(PlayerDeathEvent event) {
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of("997551922800246886")).block();
        channel.getRestChannel().createMessage(event.getDeathMessage()).subscribe();
        return true;
    }

    @EventHandler
    public boolean onConnect(PlayerJoinEvent event) {
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of("997551922800246886")).block();
        channel.getRestChannel().createMessage(event.getPlayer().getName() + " has joined the game").subscribe();
        return true;
    }

    @EventHandler
    public boolean onDisconnect(PlayerQuitEvent event) {
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of("997551922800246886")).block();
        channel.getRestChannel().createMessage(event.getPlayer().getName() + " left the game").subscribe();
        return true;
    }

    @EventHandler
    public boolean onAdvancement(PlayerAdvancementDoneEvent event) {
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of("997551922800246886")).block();
        Advancement advancement = Bukkit.getAdvancement(event.getAdvancement().getKey());
        String advancementTitle = new AdvancementInfo(advancement).getTitle();
        if (advancementTitle == null || advancementTitle.isEmpty()) {
            return false;
        }
        channel.getRestChannel().createMessage(event.getPlayer().getName() + " has completed advancement [" + advancementTitle + "]").subscribe();
        return true;
    }
}
