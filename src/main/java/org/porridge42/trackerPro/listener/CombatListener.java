package org.porridge42.trackerPro.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import org.porridge42.trackerPro.data.ArmorDataManager;
import org.porridge42.trackerPro.service.TrackingService;

import java.util.HashMap;
import java.util.Map;

//负责监听玩家战斗事件
public class CombatListener implements Listener {

    @EventHandler
    //当玩家受到伤害时，追踪盔甲的承受伤害数据
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;

        double originalDamage = e.getDamage();
        double finalDamage = e.getFinalDamage();

        double absorbedDamage = originalDamage - finalDamage;
        if (absorbedDamage <= 0) return;

        ItemStack[] armors = player.getInventory().getArmorContents();

        // 存储每件盔甲的护甲值
        Map<ItemStack, Double> armorValues = new HashMap<>();
        double totalArmorPoints = 0;

        for (ItemStack armor : armors) {
            if (!ArmorDataManager.isArmor(armor)) continue;

            double armorPoints = ArmorDataManager.getArmorPoints(armor);

            if (armorPoints <= 0) continue;

            armorValues.put(armor, armorPoints);
            totalArmorPoints += armorPoints;
        }

        if (totalArmorPoints <= 0) return;

        // 按比例分配伤害
        for (Map.Entry<ItemStack, Double> entry : armorValues.entrySet()) {
            ItemStack armor = entry.getKey();
            double armorPoints = entry.getValue();

            double damageShare = absorbedDamage * (armorPoints / totalArmorPoints);

            TrackingService.trackDamage(armor, damageShare);
        }
    }
}
