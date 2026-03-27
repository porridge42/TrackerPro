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

    public static void updater(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();
        List<Component> lore = new ArrayList<>();

        //显示发现标签（发现日期，发现者，发现结构）
        if (pdc.has(DataKeys.KEY_FOUND_DATE, PersistentDataType.STRING)) {
            lore.add(Component.text("Found at: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_FOUND_STRUCTURE,
                                    PersistentDataType.STRING)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));

            lore.add(Component.text("Found on: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_FOUND_DATE,
                                    PersistentDataType.STRING)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));

            lore.add(Component.text("Found by: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_FOUND_BY,
                                    PersistentDataType.STRING)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        //显示交易获取标签（交易日期，交易者）
        if (pdc.has(DataKeys.KEY_TRADED_DATE, PersistentDataType.STRING)) {
            lore.add(Component.text("Traded on: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_TRADED_DATE,
                                    PersistentDataType.STRING)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Traded by: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%s",
                            pdc.get(DataKeys.KEY_TRADED_BY,
                                    PersistentDataType.STRING)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        //显示伤害标签（盔甲）
        if (pdc.has(DataKeys.KEY_DAMAGE_TAKEN, PersistentDataType.DOUBLE)) {
            lore.add(Component.text("Damage Taken: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%.2f",
                            pdc.get(DataKeys.KEY_DAMAGE_TAKEN,
                                    PersistentDataType.DOUBLE)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        //显示方块挖掘数
        if (pdc.has(DataKeys.KEY_BLOCKS_MINED, PersistentDataType.INTEGER)) {
            lore.add(Component.text("Blocks Mined: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%d",
                            pdc.get(DataKeys.KEY_BLOCKS_MINED,
                                    PersistentDataType.INTEGER)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        //显示捕鱼数
        if (pdc.has(DataKeys.KEY_FISH_CAUGHT, PersistentDataType.INTEGER)) {
            lore.add(Component.text("Fish Caught: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%d",
                            pdc.get(DataKeys.KEY_FISH_CAUGHT,
                                    PersistentDataType.INTEGER)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        //显示耕地数
        if (pdc.has(DataKeys.KEY_FIELD_HOED, PersistentDataType.INTEGER)) {
            lore.add(Component.text("Farmlands Hoed: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%d",
                            pdc.get(DataKeys.KEY_FIELD_HOED,
                                    PersistentDataType.INTEGER)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        //显示玩家击杀数
        if (pdc.has(DataKeys.KEY_PLAYERS_KILLS, PersistentDataType.INTEGER)) {
            lore.add(Component.text("Players Killed: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%d",
                            pdc.get(DataKeys.KEY_PLAYERS_KILLS,
                                    PersistentDataType.INTEGER)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        //显示怪物击杀数
        if (pdc.has(DataKeys.KEY_MOBS_KILLS, PersistentDataType.INTEGER)) {
            lore.add(Component.text("Mobs killed: ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%d",
                            pdc.get(DataKeys.KEY_MOBS_KILLS,
                                    PersistentDataType.INTEGER)), NamedTextColor.DARK_GRAY))
                    .decoration(TextDecoration.ITALIC, false));
        }
        meta.lore(lore);
        item.setItemMeta(meta);
    }
}
