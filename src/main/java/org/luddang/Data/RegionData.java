package org.luddang.Data;

import org.bukkit.entity.Player;
import org.luddang.Data.info.RegionInfo;

import java.util.*;

public class RegionData {
    public static Map<String, RegionInfo> regionData = new HashMap<>();
    public static Map<Player, Set<String>> nowRegion = new HashMap<>();

    public static void release(){
        regionData.clear();
        nowRegion.clear();
    }
}
