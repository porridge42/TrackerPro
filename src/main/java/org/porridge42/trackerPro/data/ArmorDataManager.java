package org.porridge42.trackerPro.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;

//处理盔甲类物品的方法
public class ArmorDataManager {

    private static final Map<Material, Double> ARMOR_VALUES = new EnumMap<>(Material.class);

    static {
        ARMOR_VALUES.put(Material.LEATHER_HELMET, 2.0);
        ARMOR_VALUES.put(Material.LEATHER_CHESTPLATE, 3.0);
        ARMOR_VALUES.put(Material.LEATHER_LEGGINGS, 2.0);
        ARMOR_VALUES.put(Material.LEATHER_BOOTS, 1.0);
        ARMOR_VALUES.put(Material.CHAINMAIL_HELMET, 2.0);
        ARMOR_VALUES.put(Material.CHAINMAIL_CHESTPLATE, 5.0);
        ARMOR_VALUES.put(Material.CHAINMAIL_LEGGINGS, 4.0);
        ARMOR_VALUES.put(Material.CHAINMAIL_BOOTS, 1.0);
        ARMOR_VALUES.put(Material.IRON_HELMET, 2.0);
        ARMOR_VALUES.put(Material.IRON_CHESTPLATE, 6.0);
        ARMOR_VALUES.put(Material.IRON_LEGGINGS, 5.0);
        ARMOR_VALUES.put(Material.IRON_BOOTS, 2.0);
        ARMOR_VALUES.put(Material.GOLDEN_HELMET, 2.0);
        ARMOR_VALUES.put(Material.GOLDEN_CHESTPLATE, 5.0);
        ARMOR_VALUES.put(Material.GOLDEN_LEGGINGS, 3.0);
        ARMOR_VALUES.put(Material.GOLDEN_BOOTS, 1.0);
        ARMOR_VALUES.put(Material.DIAMOND_HELMET, 3.0);
        ARMOR_VALUES.put(Material.DIAMOND_CHESTPLATE, 8.0);
        ARMOR_VALUES.put(Material.DIAMOND_LEGGINGS, 6.0);
        ARMOR_VALUES.put(Material.DIAMOND_BOOTS, 3.0);
        ARMOR_VALUES.put(Material.NETHERITE_HELMET, 3.0);
        ARMOR_VALUES.put(Material.NETHERITE_CHESTPLATE, 8.0);
        ARMOR_VALUES.put(Material.NETHERITE_LEGGINGS, 6.0);
        ARMOR_VALUES.put(Material.NETHERITE_BOOTS, 3.0);
        ARMOR_VALUES.put(Material.TURTLE_HELMET, 2.0);
        ARMOR_VALUES.put(Material.COPPER_HELMET, 2.0);
        ARMOR_VALUES.put(Material.COPPER_CHESTPLATE, 6.0);
        ARMOR_VALUES.put(Material.COPPER_LEGGINGS, 5.0);
        ARMOR_VALUES.put(Material.COPPER_BOOTS, 2.0);
    }

    //获取护甲值的方法（用于计算不同类型的盔甲承受的伤害）
    public static double getArmorPoints(ItemStack item) {
        if (item == null || item.isEmpty()) {
            return 0.0;
        }

        Double value = ARMOR_VALUES.get(item.getType());
        return value != null ? value : 0.0;
    }

    //判断物品是否是盔甲的方法（用于过滤盔甲栏内其他非盔甲类装备）
    public static boolean isArmor(ItemStack item) {
        if (item == null) return false;

        Material type = item.getType();
        return type.name().endsWith("_HELMET")
                || type.name().endsWith("_CHESTPLATE")
                || type.name().endsWith("_LEGGINGS")
                || type.name().endsWith("_BOOTS");
    }
}
