package com.truerealitymc.forcespawn;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener
  implements Listener
{
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    Bukkit.getLogger().info("Detected the PlayerJoinEvent of player " + event.getPlayer().getName());
    Player player = event.getPlayer();
    if (Master.plugin.getConfig().getBoolean("enableBypassPermission"))
    {
      if (player.hasPermission("forcespawn.bypassTP"))
      {
        Bukkit.getLogger().info(player.getName() + " has the Master bypass permission.");
        return;
      }
      player.teleport(Master.getLocation(player.getLocation()));
      Bukkit.getLogger().info("Forced " + player.getName() + " to defined location.");
      return;
    }
    player.teleport(Master.getLocation(player.getLocation()));
    Bukkit.getLogger().info("Forced " + player.getName() + " to defined location.");
  }
}