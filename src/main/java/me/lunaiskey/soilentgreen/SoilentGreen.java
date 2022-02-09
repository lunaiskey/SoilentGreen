package me.lunaiskey.soilentgreen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SoilentGreen extends JavaPlugin {
    private static final String prefix = ChatColor.GREEN+"[SoilentGreen"+"\uD83C\uDF2E"+"]";
    private static SoilentGreen instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getLogger().info(prefix+" Activated! Tacos rejoice!");
        Bukkit.getLogger().info(prefix+" Enabling Villager to Taco Meat Protocol.");

        Bukkit.getPluginManager().registerEvents(new VillagerDeath(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return prefix;
    }

    public static SoilentGreen getInstance() {
        return instance;
    }
}
