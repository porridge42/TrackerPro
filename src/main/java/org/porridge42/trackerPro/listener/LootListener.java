package org.porridge42.trackerPro.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import org.porridge42.trackerPro.data.ItemDataManager;
import org.porridge42.trackerPro.service.TrackingService;

//负责监听玩家获取战利品相关的事件
public class LootListener implements Listener {

    @EventHandler
    //监听玩家点击物品事件，直接从箱子拿取物品时触发标记
    public void onTakeItem(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        if (!ItemDataManager.isNaturalLoot(item)) return;
        if (ItemDataManager.hasFoundDate(item)) return;

        TrackingService.trackLoot(item, player);
    }

    @EventHandler
    //监听玩家捡起物品事件，处理掉落物战利品与鞘翅
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack item = event.getItem().getItemStack();
        if (item.getItemMeta() == null) return;
        if (!ItemDataManager.isNaturalLoot(item)) return;

        if (!ItemDataManager.hasFoundDate(item)) {
            TrackingService.trackLoot(item, player);
            ItemDataManager.removeNaturalLootTag(item);
        }
    }
}
