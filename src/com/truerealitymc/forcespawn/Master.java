package com.truerealitymc.forcespawn;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Master extends JavaPlugin {
  public static Location teleportLocation = new Location(Bukkit.getWorld("world"), 0.0D, 400.0D, 0.0D);
  public static String name = "";
  public static String version;
  public static JavaPlugin plugin;
  public static String check;
  BukkitTask updateChecker;
  File file = getFile();
  
  public void onEnable()
  {
    plugin = this;
    
    plugin.getConfig().options().copyDefaults(true);
    saveConfig();
    plugin.reloadConfig();
    
    Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    Bukkit.getLogger().info(getDescription().getName() + " v" + getDescription().getVersion() + " has been enabled!");
  }
  
  public void onDisable()
  {
    Bukkit.getLogger().info(getDescription().getName() + " v" + getDescription().getVersion() + " has been disabled.");
  }
  
  public static void saveLocation(Location loc)
  {
    plugin.getConfig().set("location.world", loc.getWorld().getName());
    plugin.getConfig().set("location.X", Double.valueOf(loc.getX()));
    plugin.getConfig().set("location.Y", Double.valueOf(loc.getY()));
    plugin.getConfig().set("location.Z", Double.valueOf(loc.getZ()));
    
    plugin.getConfig().set("location.Pitch", Float.valueOf(loc.getPitch()));
    plugin.getConfig().set("location.Yaw", Float.valueOf(loc.getYaw()));
    plugin.saveConfig();
    
    Bukkit.getLogger().info("ForceSpawn: Saved new force login information.");
  }
  
  public static Location getLocation(Location loc)
  {
    plugin.reloadConfig();
    Location teleportLocation = new Location(null, 0.0D, 0.0D, 0.0D);
    try
    {
      teleportLocation.setWorld(Bukkit.getWorld(plugin.getConfig().getString("location.world")));
      teleportLocation.setX(plugin.getConfig().getDouble("location.X"));
      teleportLocation.setY(plugin.getConfig().getDouble("location.Y"));
      teleportLocation.setZ(plugin.getConfig().getDouble("location.Z"));
      teleportLocation.setYaw((float)plugin.getConfig().getLong("location.Yaw"));
      teleportLocation.setPitch((float)plugin.getConfig().getLong("location.Pitch"));
      return teleportLocation;
    }
    catch (Exception ex)
    {
      Bukkit.getLogger().warning("ForceSpawn location has NOT BEEN set! Type '/spawn set' set while ingame to set your position!");
    }
    return loc;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("spawn"))
    {
      if ((sender.isOp()) || (sender.hasPermission("forcespawn.admin")))
      {
        if (args.length == 0)
        {
          sender.sendMessage("");
          sender.sendMessage(ChatColor.AQUA + "===Force Spawn===");
          sender.sendMessage(ChatColor.AQUA + "/spawn " + ChatColor.GRAY + "- Displays this help page.");
          sender.sendMessage(ChatColor.AQUA + "/spawn set " + ChatColor.GRAY + "- Sets the location for the force login.");
          sender.sendMessage(ChatColor.AQUA + "/spawn go " + ChatColor.GRAY + "- Teleport to the defined location.");
          sender.sendMessage(ChatColor.GRAY + "Plugin developed by TheTrueReality.");
          Bukkit.getLogger().info(sender.getName() + " displayed ForceSpawn's help page.");
          return true;
        }
        if (args.length == 1)
        {
          if (args[0].equalsIgnoreCase("set"))
          {
            saveLocation(((Player)sender).getLocation());
            sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.AQUA + "ForceSpawn" + ChatColor.AQUA + "] " + ChatColor.GRAY + "New location saved!");
            return true;
          }
          if (args[0].equalsIgnoreCase("go"))
          {
            if ((sender instanceof Player))
            {
              Player player = (Player)sender;
              
              player.teleport(getLocation(player.getLocation()));
              return true;
            }
            sender.sendMessage(ChatColor.DARK_RED + "Only players can execute this command.");
            return true;
          }
        }
      }
      else
      {
        sender.sendMessage(ChatColor.DARK_AQUA + "You need to be a server operator to peform that command.");
        Bukkit.getLogger().info(sender.getName() + " was denied to a ForceSpawn command.");
        return true;
      }
      sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.AQUA + "ForceSpawn" + ChatColor.AQUA + "] " + ChatColor.GRAY + "Unknown command! Type /spawn for help.");
      return true;
    }
    return false;
  }
}
