package me.lopataa.grayowl.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.lopataa.grayowl.Grayowl;

public class OtherCommands implements CommandExecutor {

    Grayowl plugin;

    public OtherCommands(Grayowl plugin_arg) {
        plugin = plugin_arg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "uwu":
                uwu(sender, command, args);
                break;

        }
        return true;
    }
    boolean uwu(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player target_player;
        String target_username;

        Player sender_player = (Player) sender;
        if (args.length == 0) {
            target_player = Bukkit.getOnlinePlayers().stream().findAny().get();
            target_username = target_player.getName();
        } else {
            target_username = args[0];
            target_player = plugin.getServer().getPlayer(target_username);
            if (plugin.getServer().getPlayer(target_username) == null) {
                sender_player.sendMessage(ChatColor.RED + "/uwu: player " + target_username + " is not online.");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("uwu")) {
            //check if user is online
            if (plugin.getServer().getPlayer(target_username) != null && plugin.getServer().getPlayer(target_username).isOnline()) {
                target_player.sendTitle("uwu", "you have been uwu'd by " + ChatColor.MAGIC + sender_player.getName(), 10, 30, 10);
                target_player.playSound(target_player.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
                target_player.playSound(target_player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
                target_player.playSound(target_player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
                target_player.sendMessage(ChatColor.WHITE + "/uwu: you have been uwu'd by " + ChatColor.MAGIC + sender_player.getName());
                sender_player.sendMessage(ChatColor.WHITE + "/uwu: successfully uwu'd player " + target_username);
            } else {
                sender_player.sendMessage(ChatColor.RED + "/uwu: player " + target_username + " is not online.");
            }
        }
    }
}
