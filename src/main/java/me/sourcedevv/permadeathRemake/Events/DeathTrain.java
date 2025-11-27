package me.sourcedevv.permadeathRemake.Events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import static me.sourcedevv.permadeathRemake.PermadeathRemake.plugin;

public class DeathTrain implements Listener {

    // This is going to be used for preventing players to remove the thunder.
    public static boolean deathTrainEnabled = false;
    public static int timeLeft = 0;
    private static int stormTaskId = -1;
    private static final World mainWorld = Bukkit.getWorld("world");

    public static void startDeathTrain(int totalTime) {
        // main needed variables
        int hoursToTicks = totalTime * 20;

        // main storm logic
        if (mainWorld == null) { return; }

        deathTrainEnabled = true;

        mainWorld.setStorm(true);
        mainWorld.setThundering(true);

        mainWorld.setWeatherDuration(hoursToTicks);
        mainWorld.setThunderDuration(hoursToTicks);

        if (stormTaskId != -1) {
            Bukkit.getScheduler().cancelTask(stormTaskId);
        }

        if (deathTrainEnabled) {
            timeLeft += totalTime;
        }

        stormTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (timeLeft <= 0) {

                deathTrainEnabled = false;

                mainWorld.setStorm(false);
                mainWorld.setThundering(false);

                Bukkit.getScheduler().cancelTask(stormTaskId);
                return;

            }

            int hrs = (timeLeft % 86400) / 3600;
            int mins = (timeLeft % 3600) / 60;
            int secs = timeLeft % 60;
            String msg = String.format(
                    ChatColor.GRAY + "Quedan %02d:%02d:%02d de tormenta",
                    hrs, mins, secs
            );

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
            }

            timeLeft--;
        }, 0, 20);
    }

    @EventHandler
    public static void onWeatherChange(WeatherChangeEvent event) {
        if (deathTrainEnabled && !event.toWeatherState()) {
            // preventing the game for stopping the storm
            event.setCancelled(true);

            if (mainWorld == null) { return; }

            mainWorld.setStorm(true);
            mainWorld.setThundering(true);
        }
    }
}
