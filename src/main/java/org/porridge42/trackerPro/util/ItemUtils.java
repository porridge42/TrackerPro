package org.porridge42.trackerPro.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    // 物品工具方法类

    // 判断物品是否为高价值战利品
    public static boolean isHighValueLoot(ItemStack item) {
        if (item == null || item.getType().isAir()) return false;
        Material type = item.getType();

        if (type.getMaxDurability() > 0) {
            // 是否有耐久（工具武器护甲）
            return true;
        }
        if (type == Material.FILLED_MAP) return true;
        if (type == Material.GOAT_HORN) return true;
        if (type == Material.TRIAL_KEY) return true;
        if (type == Material.ENCHANTED_BOOK) return true;
        if (type == Material.HEART_OF_THE_SEA) return true;
        if (type == Material.ENCHANTED_GOLDEN_APPLE) return true;
        if (type == Material.BUNDLE) return true;
        if (type.name().endsWith("_BUNDLE")) return true;
        if (type.name().startsWith("MUSIC_DISC_") || type == Material.DISC_FRAGMENT_5) return true;
        if (type.name().endsWith("_HORSE_ARMOR")) return true;
        if (type.name().endsWith("_SMITHING_TEMPLATE")) return true;
        if (type.name().endsWith("_NAUTILUS_ARMOR")) return true;
        return false;
    }

    // 判断物品是否是武器
    public static boolean isWeapon (ItemStack item) {
        if (item == null || item.getType().isAir()) return false;
        Material type = item.getType();
        String name = item.getType().name();

        if (type == Material.BOW) return true;
        if (type == Material.TRIDENT) return true;
        if (type == Material.MACE) return true;
        if (type == Material.CROSSBOW) return true;

        if (name.endsWith("_SPEAR")) return true;
        if (name.endsWith("_SWORD")) return true;
        if (name.endsWith("_AXE")) return true;
        return false;
    }
}
