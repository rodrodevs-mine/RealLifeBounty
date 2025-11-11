package com.abrgamingzone.bounty.Utils;

import com.abrgamingzone.bounty.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void loadConfig() {
        config = Main.getInstance().getConfig();
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static String getMessage(String path) {
        return config.getString("messages." + path, path);
    }

    public static int getOfflinePenalty() {
        return config.getInt("balance_penalty.offline", 1200);
    }

    public static String getRewardDefault() {
        return config.getString("reward.default", "DIAMOND:1");
    }

    public static boolean isWorldAllowed(String worldName) {
        return config.getStringList("worlds_allowed").contains(worldName);
    }
}