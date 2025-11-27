package me.sourcedevv.permadeathRemake;

import me.sourcedevv.permadeathRemake.Events.DeathTrain;
import me.sourcedevv.permadeathRemake.Logic.DaySystem;
import me.sourcedevv.permadeathRemake.PlayerRelated.OnDeath;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class PermadeathRemake extends JavaPlugin {

    public static Plugin plugin;
    public static int day;

    @Override
    public void onEnable() {

        // Setting up plugin
        plugin = this;
        saveDefaultConfig();

        // Main Variables
        PluginManager manager = Bukkit.getPluginManager();

        // Registering Player related events
        manager.registerEvents(new OnDeath(), this);

        // Setting up day logic
        DaySystem.initialize();

        // Registering Main world events
        manager.registerEvents(new DeathTrain(), this);

        // Re-initializing events.
        int currentDeathTrainDuration = getConfig().getInt("death_train_duration");

        if (currentDeathTrainDuration > 0) {
            DeathTrain.startDeathTrain(currentDeathTrainDuration);
        }

        // Plugin successfully started
        System.out.println("[PERMADEATH REMAKE | SERVER INFO]: Plugin Enabled, enjoy!");
        System.out.println("[PERMADEATH REMAKE | SERVER INFO]: Author: SourceDevv");
    }

    @Override
    public void onDisable() {
        // Saving data.

        if (DeathTrain.deathTrainEnabled) {
            getConfig().set("death_train_duration", DeathTrain.timeLeft);
        }

        getConfig().set("current_day", day);

        saveConfig();

        System.out.println("[PERMADEATH REMAKE | SERVER INFO]: Plugin disabled");
    }
}
