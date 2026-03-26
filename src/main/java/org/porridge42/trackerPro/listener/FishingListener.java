package org.porridge42.trackerPro.listener;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.porridge42.trackerPro.data.ItemDataManager;
import org.porridge42.trackerPro.service.TrackingService;

// 监听玩家通过钓鱼获取战利品
public class FishingListener implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        Player player = event.getPlayer();
        if (!(event.getCaught() instanceof Item itemEntity)) return;

        ItemStack itemStack = itemEntity.getItemStack();
        ItemDataManager.makeNaturalLoot(itemStack);

        NamespacedKey key = NamespacedKey.minecraft("gameplay/fishing");
        LootTable lootTable = Bukkit.getLootTable(key);
        ItemDataManager.setFoundStructure(itemStack, lootTable);

        itemEntity.setItemStack(itemStack);
        TrackingService.trackLoot(itemStack, player);
    }
}
