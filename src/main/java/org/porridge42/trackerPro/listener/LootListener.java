package org.porridge42.trackerPro.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.porridge42.trackerPro.data.ItemDataManager;
import org.porridge42.trackerPro.service.ChestTrackingService;
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
    //监听玩家破坏箱子事件，手动掉落物品并触发标记
    public void onChestBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!(block.getState() instanceof Chest chest)) return;

        Player player = event.getPlayer();
        Location loc = block.getLocation();

        // 只处理自然生成箱子
        if (!ChestTrackingService.isNaturalChest(loc)) return;

        Inventory inv = chest.getInventory();

        for (ItemStack item : inv.getContents()) {

            if (item == null || item.getType().isAir()) continue;
            if (!ItemDataManager.isNaturalLoot(item)) continue;
            if (!ItemDataManager.hasFoundDate(item)) {

                TrackingService.trackLoot(item, player);
                // 移除 natural_loot 标记（防止重复）
                ItemDataManager.removeNaturalLootTag(item);
            }

            // 手动控制掉落
            block.getWorld().dropItemNaturally(loc, item);
        }

        // 清空箱子
        inv.clear();
        // 取消原版掉落
        event.setDropItems(false);
    }

    @EventHandler
    //监听玩家破坏运输矿车事件，手动掉落物品并触发标记（这里还没找到问题）
    public void onMinecartDestroy(VehicleDestroyEvent event) {
        if (!(event.getVehicle() instanceof StorageMinecart minecart)) return;
        if (!(event.getAttacker() instanceof Player player)) return;
        if (!ChestTrackingService.isNaturalChestsMinecraft(minecart)) return;

        event.setCancelled(true); //取消原版破坏

        Inventory inv = minecart.getInventory();
        Location loc = minecart.getLocation();

        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType().isAir()) continue;

            // 手动标记为自然生成战利品
            if (!ItemDataManager.isNaturalLoot(item)) {
                ItemDataManager.makeNaturalLoot(item);
            }

            if (!ItemDataManager.hasFoundDate(item)) {
                TrackingService.trackLoot(item, player);
                ItemDataManager.removeNaturalLootTag(item);
            }
            // 手动掉落
            minecart.getWorld().dropItemNaturally(loc, item.clone());
        }
        inv.clear();
        // 手动移除矿车
        minecart.remove();
    }

    @EventHandler
    //监听玩家捡起物品事件，处理鞘翅
    public void onPickup(EntityPickupItemEvent event) {

        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack item = event.getItem().getItemStack();

        if (item.getType() != Material.ELYTRA) return;
        if (!ItemDataManager.isNaturalLoot(item)) return;

        if (!ItemDataManager.hasFoundDate(item)) {
            TrackingService.trackLoot(item, player);
            ItemDataManager.removeNaturalLootTag(item);
        }
    }
}
