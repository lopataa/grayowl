package me.lopataa.grayowl.minecraftCommands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class DiscordCommands implements CommandExecutor {

    GatewayDiscordClient client;

    public DiscordCommands(GatewayDiscordClient client_arg) {
        client = client_arg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { sender.sendMessage("Only players can use this command!"); return true; }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("hello")) {
            //send a discord message
            Channel channel = client.getChannelById(Snowflake.of("997551922800246886")).block();
            channel.getRestChannel().createMessage(player.getDisplayName() + " said hello!").block();
        } else if (command.getName().equalsIgnoreCase("music")) {
            musicBotInteraction(String.join(" ", args));
        }

        return true;
    }

    void musicBotInteraction(String argument) {
        //send a discord message
        Channel channel = client.getChannelById(Snowflake.of("999806480565927997")).block();
        channel.getRestChannel().createMessage("$"+argument).block();
    }
}
