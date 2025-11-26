package me.sourcedevv.permadeathRemake.PlayerRelated;

import me.sourcedevv.permadeathRemake.PermadeathRemake;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeath implements Listener {

    @EventHandler
    public static void OnDeathEvent(PlayerDeathEvent event) {

        // Getting location and setting up Player death stuff
        Player player = event.getEntity();
        Location deathPosition = player.getLocation();

        // Show death messages
        SendMessages(player);

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

    private static void SendMessages(Player deadPlayer) {

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
            player.playSound(player, Sound.ENTITY_BLAZE_DEATH, 1f, .5f);

            Bukkit.getScheduler().runTaskLater(PermadeathRemake.plugin, () -> player.playSound(player, Sound.ENTITY_SKELETON_HORSE_DEATH, 1f, 1f), 5*20);
        }

    }
}
