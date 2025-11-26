package me.sourcedevv.permadeathRemake;

import me.sourcedevv.permadeathRemake.Events.DeathTrain;
import me.sourcedevv.permadeathRemake.PlayerRelated.OnDeath;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PermadeathRemake extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {

        // Setting up plugin
        plugin = this;

        // Main Variables
        PluginManager manager = Bukkit.getPluginManager();

        // Registering Player related events
        manager.registerEvents(new OnDeath(), this);

        // Registering Main world events
        manager.registerEvents(new DeathTrain(), this);

        // Plugin successfully started
        System.out.println("[SERVER INFO]: Plugin Enabled, enjoy!");
        System.out.println("[SERVER INFO]: Author: SourceDevv");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
