package org.luddang.Module.Game;

import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerMoveEvent;
import org.luddang.Data.RegionData;
import org.luddang.Data.info.RegionInfo;
import org.luddang.Luddang;
import org.luddang.Message.BaseMessage;
import org.luddang.Module.BaseModule.ConfigModule;
import org.luddang.Module.BaseModule.MessageModule;

import java.util.ArrayList;
import java.util.List;

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

        RegionData.regionData.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            RegionInfo value = entry.getValue();
        });
    }
}
