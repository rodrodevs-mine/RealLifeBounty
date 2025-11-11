package com.abrgamingzone.bounty.Commands;

import com.abrgamingzone.bounty.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BountyCommand implements CommandExecutor {

    private static final Map<UUID, UUID> bounties = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) return false;

        switch (args[0].toLowerCase()) {
            case "set" -> {
                if (args.length < 3) {
                    sender.sendMessage("Usage: /bounty set <player> <target>");
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                Player target = Bukkit.getPlayer(args[2]);
                if (player == null || target == null) {
                    sender.sendMessage("Player not online.");
                    return true;
                }
                bounties.put(player.getUniqueId(), target.getUniqueId());
                player.sendMessage(ConfigManager.getMessage("bounty_assigned").replace("{target}", target.getName()));
                target.sendMessage(ConfigManager.getMessage("bounty_target").replace("{player}", player.getName()));
            }
            case "info" -> {
                if (args.length < 2) {
                    sender.sendMessage("Usage: /bounty info <player>");
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null && bounties.containsKey(player.getUniqueId())) {
                    UUID targetUUID = bounties.get(player.getUniqueId());
                    Player target = Bukkit.getPlayer(targetUUID);
                    sender.sendMessage(player.getName() + " has bounty on " + (target != null ? target.getName() : "Offline Player"));
                } else sender.sendMessage("No bounty found.");
            }
            case "remove" -> {
                if (args.length < 2) {
                    sender.sendMessage("Usage: /bounty remove <player>");
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    bounties.remove(player.getUniqueId());
                    sender.sendMessage("Bounty removed for " + player.getName());
                }
            }
            default -> sender.sendMessage("Unknown command.");
        }
        return true;
    }

    public static Map<UUID, UUID> getBounties() {
        return bounties;
    }
}