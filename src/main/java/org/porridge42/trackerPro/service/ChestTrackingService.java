package org.porridge42.trackerPro.service;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;

//用于追踪自然生成的箱子位置，以便区分玩家放置的箱子和自然生成的箱子
public class ChestTrackingService {
    private static final Set<Location> naturalChests = new HashSet<>();
    private static final Set<Entity> naturalChestsMinecraft = new HashSet<>();

    public static void markNaturalChest(Location loc) {
        naturalChests.add(loc);
    }

    public static boolean isNaturalChest(Location loc) {
        return naturalChests.contains(loc);
    }

    public static void markNaturalChestsMinecraft(Entity entity) {
        naturalChestsMinecraft.add(entity);
    }

    public static boolean isNaturalChestsMinecraft(Entity entity) {
        return naturalChestsMinecraft.contains(entity);
    }
}
