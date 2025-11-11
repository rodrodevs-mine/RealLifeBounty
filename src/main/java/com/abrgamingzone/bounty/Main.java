package com.abrgamingzone.bounty;

import com.abrgamingzone.bounty.Commands.BountyCommand;
import com.abrgamingzone.bounty.Listeners.KillListener;
import com.abrgamingzone.bounty.Utils.ConfigManager;
import com.abrgamingzone.bounty.Utils.RewardManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static Economy econ;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        ConfigManager.loadConfig();

        if (!setupEconomy()) {
            getLogger().severe("Vault not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        RewardManager.setupEconomy(econ);

        getCommand("bounty").setExecutor(new BountyCommand());
        getServer().getPluginManager().registerEvents(new KillListener(), this);
        getLogger().info("RealLifeBounty Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RealLifeBounty Disabled!");
    }

    public static Main getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) econ = rsp.getProvider();
        return econ != null;
    }
}