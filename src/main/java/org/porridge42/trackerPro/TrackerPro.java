package org.porridge42.trackerPro;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.porridge42.trackerPro.data.keys.DataKeys;
import org.porridge42.trackerPro.listener.CombatListener;
import org.porridge42.trackerPro.listener.LootListener;

public final class TrackerPro extends JavaPlugin {

    private static TrackerPro instance;

    @Override
    public void onEnable() {
        instance = this;
        DataKeys.init(this);
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new LootListener(), this);
        pluginManager.registerEvents(new CombatListener(), this);
        getLogger().info("TrackerPro enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TrackerPro disabled!");
    }

    public static TrackerPro getInstance() {
        return instance;
    }
}
