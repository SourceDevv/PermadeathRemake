package me.sourcedevv.permadeathRemake.PlayerRelated;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static me.sourcedevv.permadeathRemake.Events.DeathTrain.startDeathTrain;
import static me.sourcedevv.permadeathRemake.PermadeathRemake.day;
import static me.sourcedevv.permadeathRemake.PermadeathRemake.plugin;

public class OnDeath implements Listener {

    @EventHandler
    public static void onDeathEvent(PlayerDeathEvent event) {

        // Getting location and setting up Player death stuff
        Player player = event.getEntity();
        Location deathPosition = player.getLocation();

        // Show death messages
        initializeDeath(player);

        Block mainBlock = deathPosition.getBlock();
        Block headBlock = mainBlock.getRelative(BlockFace.UP);
        Block bedrockBlock = mainBlock.getRelative(BlockFace.DOWN);


        // Set needed Blocks
        bedrockBlock.setType(Material.BEDROCK);
        mainBlock.setType(Material.NETHER_BRICK_FENCE);
        headBlock.setType(Material.PLAYER_HEAD);

        // Setup head data
        Skull headData = (Skull) headBlock.getState();
        headData.setOwningPlayer(player);
        headData.update();
    }

    private static void initializeDeath(Player deadPlayer) {

        // Creates title
        // Sends death screen to all players
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(
                    ChatColor.RED + "¡Permadeath!",
                    deadPlayer.getName() + " ha muerto",
                    0,
                    5*20,
                    20
            );
            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Este es el comienzo del sufrimiento eterno de " + ChatColor.DARK_RED + ChatColor.BOLD + deadPlayer.getName() + ChatColor.RED + ChatColor.BOLD + ". ¡HA SIDO PERMABANEADO!");
            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, .5f);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.playSound(player.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 1f, 1f);
                Bukkit.broadcastMessage(ChatColor.RED + "Comienza la Death Train con duracion de "+ day + (day == 1 ? " hora." : " horas."));

                startDeathTrain(day * 60 * 60);
            }, 5*20);

        }

    }
}
