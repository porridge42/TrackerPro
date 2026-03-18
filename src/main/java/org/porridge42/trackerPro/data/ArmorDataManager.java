package org.porridge42.trackerPro.data;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

//处理盔甲类物品的方法
public class ArmorDataManager {

    //获取护甲值的方法（用于计算不同类型的盔甲承受的伤害）
    public static double getArmorPoints(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return 0;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0;

        double armor = 0;

        if (meta.hasAttributeModifiers()) {
            Collection<AttributeModifier> modifiers =
                    meta.getAttributeModifiers(Attribute.GENERIC_ARMOR);

            if (modifiers != null) {
                for (AttributeModifier modifier : modifiers) {
                    armor += modifier.getAmount();
                }
            }
        }

        // 如果没有护甲属性，提供默认值
        if (armor == 0) {
            Material type = item.getType();
            if (type.name().endsWith("_HELMET")) armor = 2;
            else if (type.name().endsWith("_CHESTPLATE")) armor = 6;
            else if (type.name().endsWith("_LEGGINGS")) armor = 5;
            else if (type.name().endsWith("_BOOTS")) armor = 1;
        }

        return armor;
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
