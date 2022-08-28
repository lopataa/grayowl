package me.lopataa.grayowl;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import me.lopataa.grayowl.minecraftListeners.onMessageListener;
import me.lopataa.grayowl.minecraftCommands.DiscordCommands;
import me.lopataa.grayowl.minecraftCommands.OtherCommands;
import me.lopataa.grayowl.minecraftListeners.InGameListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Grayowl extends JavaPlugin {

    public Logger logger = Bukkit.getLogger();

    public static GatewayDiscordClient client;

    Boolean debug = true; //DON'T FORGET TO TURN THIS OFF IN PRODUCTION

    @Override
    public void onEnable() {

        // generate config file if it doesn't exist
        saveDefaultConfig();

        // Plugin startup logic
        BaseComponent[] component =
                new ComponentBuilder("Hello ").color(ChatColor.RED)
                        .append("world").color(ChatColor.DARK_RED).bold(true)
                        .append("!").color(ChatColor.RED).create();


        // Discord stuff
        client = DiscordClient.create(getConfig().getString("discord_token"))
                .gateway()
                .withEventDispatcher(d -> d.on(ReadyEvent.class)
                        .doOnNext(readyEvent -> logger.info(String.format("Ready: %s", readyEvent.getSelf().getUsername()))))
                .login().block();
        client.updatePresence(ClientPresence.online(ClientActivity.playing("on " + getConfig().getString("server_name")))).subscribe();
        Channel channel = client.getChannelById(Snowflake.of(getConfig().getString("chat.channel"))).block();
        channel.getRestChannel().createMessage("Server is now online").block();
        client.onDisconnect().subscribe(disconnectEvent -> {
            if (getConfig().getBoolean("chat.messages.server") == true) {
                channel.getRestChannel().createMessage("Server is now offline").block();
            }
        });

        // Register commands
        if (getConfig().getBoolean("commands.other.hello.enabled") == true) {
            getCommand("hello").setExecutor(new DiscordCommands(client));
        }
        if (getConfig().getBoolean("commands.other.uwu.enabled") == true) {
            getCommand("uwu").setExecutor(new OtherCommands(this));
        }
        if (getConfig().getBoolean("music.enabled") == true) {
            getCommand("music").setExecutor(new DiscordCommands(client));
        }

        // Register listeners
        if (getConfig().getBoolean("chat.send_from_discord") == true) {
            new onMessageListener(client, this);
        }

        // Register events
        getServer().getPluginManager().registerEvents(new InGameListener(client, this), this);


    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
