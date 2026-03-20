package org.porridge42.trackerPro.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Barrel;
import org.bukkit.block.Chest;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.porridge42.trackerPro.data.ItemDataManager;

//负责监听战利品生成事件
public class LootGenerateListener implements Listener {

    @EventHandler
    //监听战利品生成，标记自然生成的战利品与所在结构
    public void onLootGenerate(LootGenerateEvent event) {

        NamespacedKey key = event.getLootTable().getKey();
        String k = key.toString();

        // 过滤非结构战利品
        boolean isStructureLoot = k.startsWith("minecraft:chests/");

        // 过滤非容器战利品
        InventoryHolder holder = event.getInventoryHolder();
        boolean isContainer = holder instanceof Chest ||
                holder instanceof Barrel ||
                holder instanceof StorageMinecart;

        for (ItemStack item : event.getLoot()) {
            if (item == null || item.getType().isAir()) continue;

            if (isStructureLoot && isContainer) {
                ItemDataManager.makeNaturalLoot(item);
                ItemDataManager.setFoundStructure(item, event.getLootTable());
            }
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
}
