package me.border.netherroofprotection;

import me.border.netherroofprotection.listeners.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class NetherRoofProtection extends JavaPlugin {

    public static Set<World> disabledWorlds = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        for (String name : getConfig().getStringList("DisabledWorlds")){
            World world = Bukkit.getWorld(name);
            if (world != null)
                disabledWorlds.add(world);
            else
                Bukkit.getLogger().info("Cannot add world `" + name + "` to the disabled worlds list since it doesn't exist.");
        }

        getServer().getPluginManager().registerEvents(new PlayerHandler(), this);
    }
}
