package org.porridge42.trackerPro.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.porridge42.trackerPro.service.TrackingService;

// 监听村民交易
public class TradeListener implements Listener {

    @EventHandler
    public void onTradeClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getInventory().getType() != InventoryType.MERCHANT) return;
        if (event.getRawSlot() != 2) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (!item.getType().isAir()) {
            TrackingService.trackTrade(item, player);
        }
    }
}
