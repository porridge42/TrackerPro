package org.porridge42.trackerPro.service;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.porridge42.trackerPro.data.ItemDataManager;
import org.porridge42.trackerPro.data.keys.DataKeys;
import org.porridge42.trackerPro.util.ItemUtils;

//数据追踪服务统一入口，负责处理所有数据追踪相关的逻辑
public class TrackingService {

    //追踪战利品获得数据
    public static void trackLoot(ItemStack item, Player player) {
        if (!ItemUtils.isHighValueLoot(item)) return; // 过滤非高价值战利品

        ItemDataManager.setFoundDate(item);
        ItemDataManager.setFoundBy(item, player.getName());
        LogService.logFound(item, player);
        LoreService.updater(item);
        ItemDataManager.removeNaturalLootTag(item);
    }

    //追踪承受伤害数据（主要是盔甲）
    public static void trackDamage(ItemStack item, double damage) {
        ItemDataManager.addDoubleData(item, DataKeys.KEY_DAMAGE_TAKEN, damage);
        LoreService.updater(item);
    }

    //追踪鱼竿使用次数
    public static void trackFishing(ItemStack item) {
        ItemDataManager.addIntegerData(item, DataKeys.KEY_FISH_CAUGHT, 1);
        LoreService.updater(item);
    }

    //追踪锄头使用次数
    public static void trackHoed(ItemStack item) {
        ItemDataManager.addIntegerData(item, DataKeys.KEY_FIELD_HOED, 1);
        LoreService.updater(item);
    }

    //追钟方块挖掘次数（镐，斧，铲，锄）
    public static void trackMined(ItemStack item) {
        ItemDataManager.addIntegerData(item, DataKeys.KEY_BLOCKS_MINED, 1);
        LoreService.updater(item);
    }

    //追踪怪物击杀数
    public static void trackModsKill(ItemStack item) {
        ItemDataManager.addIntegerData(item, DataKeys.KEY_MOBS_KILLS, 1);
        LoreService.updater(item);
    }

    //追踪玩家击杀数
    public static void trackPlaysKill(ItemStack item) {
        ItemDataManager.addIntegerData(item, DataKeys.KEY_PLAYERS_KILLS, 1);
        LoreService.updater(item);
    }
}
