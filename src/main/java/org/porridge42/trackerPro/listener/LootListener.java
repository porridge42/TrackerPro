package org.porridge42.trackerPro.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.porridge42.trackerPro.data.ItemDataManager;
import org.porridge42.trackerPro.service.ChestTrackingService;
import org.porridge42.trackerPro.service.TrackingService;

//负责监听玩家与战利品相关的事件
public class LootListener implements Listener {

    @EventHandler
    //监听战利品生成，标记自然生成的战利品
    public void onLootGenerate(LootGenerateEvent event) {

        for (ItemStack item : event.getLoot()) {

            if (item == null || item.getType().isAir()) continue;
            ItemDataManager.makeNaturalLoot(item);
        }
    }

    @EventHandler
    //监听箱子生成，标记自然生成的箱子位置
    public void onChestsGenerate(LootGenerateEvent event) {

        if (event.getInventoryHolder() instanceof Chest chest) {
            Location loc = chest.getBlock().getLocation();
            ChestTrackingService.markNaturalChest(loc);
        }
    }

    @EventHandler
    //监听物品实体生成，标记自然生成的鞘翅
    public void onItemSpawn(EntitySpawnEvent e) {

        if (!(e.getEntity() instanceof org.bukkit.entity.Item itemEntity)) return;

        ItemStack item = itemEntity.getItemStack();

        if (item.getType() != Material.ELYTRA) return;
        if (!itemEntity.getWorld().getName().equals("world_the_end")) return;

        // 标记自然战利品
        if (!ItemDataManager.isNaturalLoot(item)) {
            ItemDataManager.makeNaturalLoot(item);
        }
    }

    @EventHandler
    //监听玩家点击物品事件，直接从箱子拿触发
    public void onTakeItem(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player player)) return;

        ItemStack item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        // 核心：必须是“自然生成的物品”
        if (!ItemDataManager.isNaturalLoot(item)) return;

        // 已经记录过就跳过
        if (ItemDataManager.hasFoundDate(item)) return;

        TrackingService.trackLoot(item, player);
    }

    @EventHandler
    //监听玩家破坏箱子事件，手动掉落物品并触发
    public void onChestBreak(BlockBreakEvent e) {

        if (!(e.getPlayer() instanceof Player player)) return;

        Block block = e.getBlock();

        // 只处理箱子
        if (!(block.getState() instanceof Chest chest)) return;

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

            // 手动掉落
            block.getWorld().dropItemNaturally(loc, item);
        }

        // 清空箱子
        inv.clear();
        // 取消原版掉落
        e.setDropItems(false);
    }

    @EventHandler
    //监听玩家捡起物品事件，处理鞘翅
    public void onPickup(EntityPickupItemEvent e) {

        if (!(e.getEntity() instanceof Player player)) return;

        ItemStack item = e.getItem().getItemStack();

        if (item.getType() != Material.ELYTRA) return;
        if (!ItemDataManager.isNaturalLoot(item)) return;

        if (!ItemDataManager.hasFoundDate(item)) {
            TrackingService.trackLoot(item, player);
            ItemDataManager.removeNaturalLootTag(item);
        }
    }
}
