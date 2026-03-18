package org.porridge42.trackerPro.data;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import org.porridge42.trackerPro.data.keys.DataKeys;

import java.time.LocalDate;

//物品数据管理器，用于集中操作物品的pdc数据
public class ItemDataManager {

    //设置战利品发现者
    public static void setFoundBy(ItemStack item, String player) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();

        if (!pdc.has(DataKeys.KEY_FOUND_BY, PersistentDataType.STRING)) {
            pdc.set(DataKeys.KEY_FOUND_BY, PersistentDataType.STRING, player);
        }
        item.setItemMeta(meta);
    }
    //设置战利品发现时间
    public static void setFoundDate(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();

        if (!pdc.has(DataKeys.KEY_FOUND_DATE, PersistentDataType.STRING)) {
            pdc.set(DataKeys.KEY_FOUND_DATE, PersistentDataType.STRING,
                    LocalDate.now().toString());
        }
        item.setItemMeta(meta);
    }
    //检查是否有Found数据
    public static boolean hasFoundDate(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        return meta.getPersistentDataContainer()
                .has(DataKeys.KEY_FOUND_DATE, PersistentDataType.STRING);
    }
    //添加自然生成战利品标记
    public static void makeNaturalLoot(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();

        pdc.set(DataKeys.KEY_NATURAL_LOOT, PersistentDataType.BYTE, (byte)1);

        item.setItemMeta(meta);
    }
    //检查是否是自然生成的战利品
    public static boolean isNaturalLoot(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        return meta.getPersistentDataContainer()
                .has(DataKeys.KEY_NATURAL_LOOT, PersistentDataType.BYTE);
    }
    //移除自然生成战利品标记（已被玩家获得后）
    public static void removeNaturalLootTag(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();

        pdc.remove(DataKeys.KEY_NATURAL_LOOT);

        item.setItemMeta(meta);
    }

    //设置小数累加数据（主要是计算伤害）
    public static void addDoubleData(ItemStack item, NamespacedKey key, double value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();

        double current = pdc.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
        pdc.set(key, PersistentDataType.DOUBLE, current + value);

        item.setItemMeta(meta);
    }
}
