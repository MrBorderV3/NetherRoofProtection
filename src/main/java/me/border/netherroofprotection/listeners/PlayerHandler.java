package me.border.netherroofprotection.listeners;

import me.border.netherroofprotection.NetherRoofProtection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerHandler implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Location to = e.getTo();
        World world = to.getWorld();
        if (world == null)
            return;

        if (!NetherRoofProtection.disabledWorlds.contains(world) && world.getEnvironment() == World.Environment.NETHER){
            double y = to.getY();
            if (y >= 128){
                Player p = e.getPlayer();
                p.sendMessage(ChatColor.RED + "You may not walk on the nether roof!");
                int highestBlock = getHighestSafeBlock(to);
                if (highestBlock == -1){
                    p.teleport(e.getFrom());
                } else {
                    p.teleport(new Location(to.getWorld(), to.getX(), highestBlock, to.getZ()));
                }
            }
        }
    }

    /**
     * Get the highest safe two air block space in a location
     *
     * @param loc The location to search
     * @return The y coordinate of the block. Returns -1 if non found.
     */
    public int getHighestSafeBlock(Location loc){
        boolean foundAir = false;
        for (int i = 128; i >= 0; i--){
            Location tempLoc = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());
            if (tempLoc.getBlock().getType() == Material.AIR){
                if (foundAir) {
                    Block under = tempLoc.getBlock().getRelative(BlockFace.DOWN);
                    if (under.getType().isSolid() && under.getType() != Material.LAVA && under.getType().isBlock()) {
                        return i;
                    }
                } else {
                    foundAir = true;
                }
            } else {
                foundAir = false;
            }
        }

        return -1;
    }
}
