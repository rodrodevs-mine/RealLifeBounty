package com.abrgamingzone.bounty.Utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RewardManager {

    private static Economy econ;

    public static void setupEconomy(Economy economy) {
        econ = economy;
    }

    public static void giveReward(Player player, String rewardString) {
        String[] parts = rewardString.split(":");
        Material mat = Material.getMaterial(parts[0].toUpperCase());
        int amount = 1;
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
        if (mat != null) {
            ItemStack item = new ItemStack(mat, amount);
            player.getInventory().addItem(item);
        }
    }

    public static void deductOffline(String playerName) {
        if (econ != null) {
            econ.withdrawPlayer(playerName, ConfigManager.getOfflinePenalty());
        }
    }
}