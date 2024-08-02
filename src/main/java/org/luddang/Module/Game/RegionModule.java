package org.luddang.Module.Game;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.luddang.Data.RegionData;
import org.luddang.Data.info.RegionInfo;
import org.luddang.Luddang;
import org.luddang.Message.BaseMessage;
import org.luddang.Module.Base.ConfigModule;
import org.luddang.Module.Base.MessageModule;

import java.util.*;

public class RegionModule {

    private final MessageModule messageModule;
    private final ConfigModule configModule;

    public RegionModule(Luddang plugin) {
        this.messageModule = new MessageModule(plugin);
        this.configModule = new ConfigModule(plugin);
    }

    public void addRegion(CommandSender sender, String[] data) {

        if (data.length < 9 || !(data[1].equals(BaseMessage.COMMAND_ADD.getMessage()))) {
            messageModule.sendPlayerC(sender, BaseMessage.ERROR_COMMAND.getMessage());
            return;
        }
        List<Double> positions = new ArrayList<>();
        for (int i = 3; i < data.length; i++) {
            double position = Double.parseDouble(data[i]);
            positions.add(position);
        }

        configModule.addRegion(data[2], positions);
        configModule.getRegion();
        messageModule.sendPlayerC(sender, BaseMessage.INFO_ADD_REGION.getMessage());
    }

    public void isRegion(PlayerMoveEvent event) {
        if (RegionData.regionData.isEmpty()) return;


        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        for (Map.Entry<String, RegionInfo> entry : RegionData.regionData.entrySet()) {
            String regionName = entry.getKey();
            RegionInfo regionInfo = entry.getValue();

            double minX = Math.min(regionInfo.getX1(), regionInfo.getX2());
            double minY = Math.min(regionInfo.getY1(), regionInfo.getY2());
            double minZ = Math.min(regionInfo.getZ1(), regionInfo.getZ2());

            double maxX = Math.max(regionInfo.getX1(), regionInfo.getX2());
            double maxY = Math.max(regionInfo.getY1(), regionInfo.getY2());
            double maxZ = Math.max(regionInfo.getZ1(), regionInfo.getZ2());

            if (isInside(playerLocation, minX, minY, minZ, maxX, maxY, maxZ)) {
                Set<String> regions = RegionData.nowRegion.computeIfAbsent(player, k -> new HashSet<>());
                if (regions.isEmpty() || !regions.contains(regionName)) {
                    messageModule.sendPlayerC(player, regionName + BaseMessage.INFO_ENTER_REGION.getMessage());
                }
                regions.add(regionName);
            } else if (RegionData.nowRegion.containsKey(player) && RegionData.nowRegion.get(player).contains(regionName)) {
                RegionData.nowRegion.get(player).remove(regionName);
                messageModule.sendPlayerC(player, regionName + BaseMessage.INFO_QUIT_REGION.getMessage());
            }
        }
    }

    private Boolean isInside(Location location, double x1, double y1, double z1, double x2, double y2, double z2) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        return x >= x1 && x <= x2
                && y >= y1 && y <= y2
                && z >= z1 && z <= z2;
    }
}
