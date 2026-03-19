package org.porridge42.trackerPro.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.porridge42.trackerPro.data.ItemDataManager;
import org.porridge42.trackerPro.service.ChestTrackingService;

//负责监听战利品生成事件
public class LootGenerateListener implements Listener {

    @EventHandler
    //监听战利品生成，标记自然生成的战利品与所在结构
    public void onLootGenerate(LootGenerateEvent event) {

        for (ItemStack item : event.getLoot()) {

            if (item == null || item.getType().isAir()) continue;
            ItemDataManager.makeNaturalLoot(item);
            ItemDataManager.setFoundStructure(item, event.getLootTable());
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
    public void onItemSpawn(EntitySpawnEvent event) {

        if (!(event.getEntity() instanceof org.bukkit.entity.Item itemEntity)) return;

        ItemStack item = itemEntity.getItemStack();

        if (item.getType() != Material.ELYTRA) return;
        if (!itemEntity.getWorld().getName().equals("world_the_end")) return;

        // 标记自然战利品
        if (!ItemDataManager.isNaturalLoot(item)) {
            ItemDataManager.makeNaturalLoot(item);
            //手动标记为末地城
            NamespacedKey key = NamespacedKey.minecraft("chests/end_city_treasure");
            LootTable table = Bukkit.getLootTable(key);
            ItemDataManager.setFoundStructure(item, table);
        }
    }

    //监听运输矿车生成，标记自然生成的运输矿车
    @EventHandler
    public void onMinecraftSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof StorageMinecart)) return;
        Entity minecraft = event.getEntity();
        ChestTrackingService.markNaturalChestsMinecraft(minecraft);
    }
}
