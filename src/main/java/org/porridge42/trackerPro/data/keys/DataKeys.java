package org.porridge42.trackerPro.data.keys;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

//pdc键名的统一管理
public class DataKeys {

    public static NamespacedKey KEY_FOUND_BY;
    public static NamespacedKey KEY_FOUND_DATE;
    public static NamespacedKey KEY_FOUND_STRUCTURE;

    public static NamespacedKey KEY_DAMAGE_TAKEN;
    public static NamespacedKey KEY_BLOCKS_MINED;
    public static NamespacedKey KEY_MOBS_KILLS;
    public static NamespacedKey KEY_PLAYERS_KILLS;
    public static NamespacedKey KEY_FISH_CAUGHT;
    public static NamespacedKey KEY_FIELD_HOED;

    public static NamespacedKey KEY_NATURAL_LOOT;

    public static void init(Plugin plugin) {
        KEY_FOUND_BY = new NamespacedKey(plugin, "found_by");
        KEY_FOUND_DATE = new NamespacedKey(plugin, "found_date");
        KEY_FOUND_STRUCTURE = new NamespacedKey(plugin, "found_structure");

        KEY_DAMAGE_TAKEN = new NamespacedKey(plugin, "damage_taken");
        KEY_BLOCKS_MINED = new NamespacedKey(plugin, "blocks_mined");
        KEY_MOBS_KILLS = new NamespacedKey(plugin, "mobs_kills");
        KEY_PLAYERS_KILLS = new NamespacedKey(plugin, "players_kills");
        KEY_FISH_CAUGHT = new NamespacedKey(plugin, "fish_caught");
        KEY_FIELD_HOED = new NamespacedKey(plugin, "field_hoed");

        KEY_NATURAL_LOOT = new NamespacedKey(plugin, "natural_loot");
    }

}
