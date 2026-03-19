package org.porridge42.trackerPro.service;

import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.porridge42.trackerPro.data.keys.DataKeys;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;
import java.util.ArrayList;

//更新物品Lore标签显示服务
public class LoreService {

    // 这个方法负责更新发现标签这种一般只更新一次的lore
    public static void idleUpdater(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();
        List<Component> lore = new ArrayList<>();

        //显示发现标签（发现日期，发现者，发现结构）
        if (pdc.has(DataKeys.KEY_FOUND_DATE, PersistentDataType.STRING)) {
            lore.add(Component.text("Found in: ", NamedTextColor.WHITE)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_FOUND_STRUCTURE,
                                    PersistentDataType.STRING)), NamedTextColor.GRAY))
                    .decoration(TextDecoration.ITALIC, false));

            lore.add(Component.text("Found on: ", NamedTextColor.WHITE)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_FOUND_DATE,
                                    PersistentDataType.STRING)), NamedTextColor.GRAY))
                    .decoration(TextDecoration.ITALIC, false));

            lore.add(Component.text("Found by: ", NamedTextColor.WHITE)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_FOUND_BY,
                                    PersistentDataType.STRING)), NamedTextColor.GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    // 这个方法负责更新使用次数这种需要经常更新的lore
    public static void busyUpdater(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();
        List<Component> lore = new ArrayList<>();

        //显示伤害标签（盔甲）
        if (pdc.has(DataKeys.KEY_DAMAGE_TAKEN, PersistentDataType.DOUBLE)) {
            lore.add(Component.text("Damage Taken: ", NamedTextColor.WHITE)
                    .append(Component.text(String.format("%.2f",
                            pdc.get(DataKeys.KEY_DAMAGE_TAKEN,
                                    PersistentDataType.DOUBLE)), NamedTextColor.GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        meta.lore(lore);
        item.setItemMeta(meta);
    }
}
