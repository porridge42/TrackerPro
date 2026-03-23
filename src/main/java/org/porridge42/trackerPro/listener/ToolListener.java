package org.porridge42.trackerPro.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.porridge42.trackerPro.service.TrackingService;

// 监听玩家使用工具事件，追踪工具使用次数
public class ToolListener implements Listener {

    @EventHandler
    // 监听鱼竿捕鱼
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return; // 没钓上不触发
        Player player = event.getPlayer();

        ItemStack rod = player.getInventory().getItemInMainHand();
        if (rod.getType() != Material.FISHING_ROD) {
            rod = player.getInventory().getItemInOffHand();
        }
        TrackingService.trackFishing(rod);
    }

    @EventHandler
    // 监听锄头耕地
    public void onPlayHod(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();

        if (block == null || item == null) return;

        // 判断是否是锄头
        if (!item.getType().name().endsWith("_HOE")) return;
        // 判断方块是否可耕
        Material type = block.getType();
        if (type == Material.DIRT ||
                type == Material.GRASS_BLOCK || type == Material.DIRT_PATH ||
                type == Material.COARSE_DIRT || type == Material.ROOTED_DIRT) {
            TrackingService.trackHoed(item);
        }
    }

    @EventHandler
    // 监听方块挖掘
    public void onBlockMined(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (tool.getType().isAir()) return;
        String toolName = tool.getType().name();
        // 只追踪挖掘工具
        if (toolName.endsWith("_PICKAXE")
                || toolName.endsWith("_AXE")
                || toolName.endsWith("_SHOVEL")
                || toolName.endsWith("_HOE")) {
            TrackingService.trackMined(tool);
        }
    }
}


