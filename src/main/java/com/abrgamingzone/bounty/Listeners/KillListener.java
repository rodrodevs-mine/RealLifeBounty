package com.abrgamingzone.bounty.Listeners;

import com.abrgamingzone.bounty.Commands.BountyCommand;
import com.abrgamingzone.bounty.Utils.ConfigManager;
import com.abrgamingzone.bounty.Utils.RewardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class KillListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player dead = event.getEntity();
        Player killer = dead.getKiller();
        if (killer == null) return;

        if (!ConfigManager.isWorldAllowed(dead.getWorld().getName())) return;

        UUID killerUUID = killer.getUniqueId();
        UUID deadUUID = dead.getUniqueId();

        if (BountyCommand.getBounties().containsKey(killerUUID) && BountyCommand.getBounties().get(killerUUID).equals(deadUUID)) {
            RewardManager.giveReward(killer, ConfigManager.getRewardDefault());
            killer.sendMessage(ConfigManager.getMessage("bounty_completed")
                    .replace("{target}", dead.getName())
                    .replace("{reward}", ConfigManager.getRewardDefault()));
            BountyCommand.getBounties().remove(killerUUID);
        }
    }
}