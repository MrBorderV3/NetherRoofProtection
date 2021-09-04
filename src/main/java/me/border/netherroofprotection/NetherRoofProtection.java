package me.border.netherroofprotection;

import me.border.netherroofprotection.listeners.PlayerHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class NetherRoofProtection extends JavaPlugin {

    public static Set<String> disabledWorlds = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        for (String name : getConfig().getStringList("DisabledWorlds")){
            disabledWorlds.add(name.toLowerCase());
        }

        getServer().getPluginManager().registerEvents(new PlayerHandler(), this);
    }
}
