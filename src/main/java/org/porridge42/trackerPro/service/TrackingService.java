package org.porridge42.trackerPro.service;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.porridge42.trackerPro.data.ItemDataManager;
import org.porridge42.trackerPro.data.keys.DataKeys;

//数据追踪服务统一入口，负责处理所有数据追踪相关的逻辑
public class TrackingService {

    // 判断是否为高价值战利品
    private static boolean isHighValueLoot(ItemStack item) {
        if (item == null || item.getType().isAir()) return false;
        Material type = item.getType();

        if (type.getMaxDurability() > 0) {
            // 是否有耐久（工具武器护甲）
            return true;
        }
        if (type == Material.MAP) return true;
        if (type == Material.GOAT_HORN) return true;
        if (type == Material.TRIAL_KEY) return true;
        if (type == Material.ENCHANTED_BOOK) return true;
        if (type == Material.HEART_OF_THE_SEA) return true;
        if (type == Material.ENCHANTED_GOLDEN_APPLE) return true;
        if (type.name().startsWith("MUSIC_DISC_") || type == Material.DISC_FRAGMENT_5) return true;
        if (type.name().endsWith("_HORSE_ARMOR")) return true;
        if (type.name().endsWith("_SMITHING_TEMPLATE")) return true;

        return false;
    }

    //追踪战利品获得数据
    public static void trackLoot(ItemStack item, Player player) {
        if (!isHighValueLoot(item)) return; // 过滤非高价值战利品

        ItemDataManager.setFoundDate(item);
        ItemDataManager.setFoundBy(item, player.getName());
        LoreService.update(item);
        ItemDataManager.removeNaturalLootTag(item);
    }

    //追踪承受伤害数据（主要是盔甲）
    public static void trackDamage(ItemStack item, double damage) {
        ItemDataManager.addDoubleData(item, DataKeys.KEY_DAMAGE_TAKEN, damage);
        LoreService.update(item);
    }
}
