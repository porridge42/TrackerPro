package org.porridge42.trackerPro.service;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

//用于追踪自然生成的箱子位置，以便区分玩家放置的箱子和自然生成的箱子
public class ChestTrackingService {
    private static final Set<Location> naturalChests = new HashSet<>();

    public static void markNaturalChest(Location loc) {
        naturalChests.add(loc);
    }

    public static boolean isNaturalChest(Location loc) {
        return naturalChests.contains(loc);
    }
}
