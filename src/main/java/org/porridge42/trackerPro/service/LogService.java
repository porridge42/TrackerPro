package org.porridge42.trackerPro.service;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.porridge42.trackerPro.TrackerPro;
import org.porridge42.trackerPro.data.keys.DataKeys;

public class LogService {
    // 该服务负责控制控制台输出日志格式消息

    private static String getDimensionName(Player player) {
        return switch (player.getWorld().getEnvironment()) {
            case NORMAL -> "Overworld";
            case NETHER -> "The Nether";
            case THE_END -> "The End";
            default -> "Unknown";
        };
    }

    public static void logFound(ItemStack item, Player player) {
        if (item == null|| player == null) return;
        if (!item.hasItemMeta()) return;
        TrackerPro plugin = TrackerPro.getInstance();
        ItemMeta meta = item.getItemMeta();
        var pdc = meta.getPersistentDataContainer();

        plugin.getLogger().info("<" + getDimensionName(player) + ">" +
                player.getName() + " found a " + item.getType() + " at " +
                pdc.get(DataKeys.KEY_FOUND_STRUCTURE, PersistentDataType.STRING));
    }
}
