package me.sourcedevv.permadeathRemake.Logic;

import org.bukkit.Bukkit;

import static me.sourcedevv.permadeathRemake.PermadeathRemake.day;
import static me.sourcedevv.permadeathRemake.PermadeathRemake.plugin;

public class DaySystem {

    public static void initialize() {

        // Setting up startup day logic
        long lastDate = plugin.getConfig().getLong("last_server_connection");

        // If the plugin already started previously.
        day = plugin.getConfig().getInt("current_day");
        if (lastDate == 0) {
            // Setting up the first ever server startup
            plugin.getConfig().set("last_server_connection", System.currentTimeMillis() / 1000);
            plugin.saveConfig();
        } else {
            dayCheck(lastDate);
        }

        onRuntimeCheck();
    }

    private static void onRuntimeCheck() {
        // Async timer that runs every hour checking if the day already passed.
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {

            long lastDate = plugin.getConfig().getLong("last_server_connection");
            dayCheck(lastDate);

        }, 0, 20 * 60 * 60);
    }

    private static void dayCheck(long lastDate) {
        long currentDate = System.currentTimeMillis() / 1000;
        long daySeconds = 24 * 60 * 60;

        if (currentDate - lastDate >= daySeconds) {
            plugin.getConfig().set("last_server_connection", lastDate + daySeconds);
            plugin.saveConfig();

            day++;
            // TODO: Add difficulty changes based on days.
        }
    }

}
