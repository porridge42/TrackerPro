package org.porridge42.trackerPro.data;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootTable;
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

    //设置战利品发现结构
    public static void setFoundStructure(ItemStack item, LootTable table) {
        if (table == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        String path = table.getKey().toString();
        String structureName;

        if (path.contains("ancient_city")) structureName = "Ancient City"; // 古代城市
        else if (path.contains("bastion")) structureName = "Bastion Remnant"; // 堡垒遗迹
        else if (path.contains("buried_treasure")) structureName = "Buried Treasure"; // 埋藏的宝藏
        else if (path.contains("end_city")) structureName = "End City"; // 末地城
        else if (path.contains("nether_bridge")) structureName = "Fortress"; // 下界要塞
        else if (path.contains("mansion")) structureName = "Mansion"; //林地府邸
        else if (path.contains("mineshaft")) structureName = "Mineshaft"; //废弃矿井
        else if (path.contains("monument")) structureName = "Monument"; //海底神殿
        else if (path.contains("underwater_ruin")) structureName = "Ocean Ruin"; //海底废墟
        else if (path.contains("ruined_portal")) structureName = "Ruined Portal"; //废弃传送门
        else if (path.contains("shipwreck")) structureName = "Shipwreck"; //沉船
        else if (path.contains("stronghold")) structureName = "Stronghold"; //要塞
        else if (path.contains("pillager_outpost")) structureName = "Pillager Outpost"; //掠夺者前哨站
        else if (path.contains("desert_pyramid")) structureName = "Desert Pyramid"; //沙漠神殿
        else if (path.contains("igloo")) structureName = "Igloo"; //雪屋
        else if (path.contains("jungle_temple")) structureName = "Jungle Pyramid"; //丛林神庙
        else if (path.contains("trail_ruins")) structureName = "Trail Ruins"; //古迹废墟
        else if (path.contains("trial_chambers")) structureName = "Trail Chambers"; //试炼密室
        else if (path.contains("village")) structureName = "Village"; //村庄
        else if (path.contains("simple_dungeon")) structureName = "Dungeon"; //刷怪房
        else structureName = "Unknown Structure";

        var pdc = meta.getPersistentDataContainer();

        if (!pdc.has(DataKeys.KEY_FOUND_STRUCTURE, PersistentDataType.STRING)) {
            pdc.set(DataKeys.KEY_FOUND_STRUCTURE, PersistentDataType.STRING,
                    structureName);
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
