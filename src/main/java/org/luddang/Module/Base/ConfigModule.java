package org.luddang.Module.Base;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.luddang.Data.PlayerData;
import org.luddang.Data.RegionData;
import org.luddang.Data.TaskData;
import org.luddang.Data.info.RegionInfo;
import org.luddang.Luddang;
import org.luddang.Message.BaseMessage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ConfigModule {
    private final MessageModule messageModule;
    private final Luddang plugin;
    private final String moneyConfigYml = "player";
    private final String money = "money";
    private final String regionConfigYml = "region";
    private final String region = "region";

    public ConfigModule(Luddang plugin) {
        this.plugin = plugin;
        this.messageModule = new MessageModule(plugin);
    }


    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            // 파일이 존재하지 않으면 기본 설정을 생성
            try {
                if (new File(plugin.getDataFolder(), fileName).createNewFile())
                    messageModule.logInfo(BaseMessage.ERROR_CONFIG.getMessage());
            } catch (IOException exception) {
                messageModule.logInfo(BaseMessage.ERROR_CONFIG.getMessage());
            }
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig(FileConfiguration config, String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void initialize() {
        reloadMoney();
        getRegion();
    }

    public void release() {
        TaskData.release();
        RegionData.release();
        PlayerData.release();
    }

    public void addMoney(String uuid, Long value) {
        FileConfiguration config = getConfig(moneyConfigYml + "/" + uuid + ".yml");
        config.set(money, value);
        saveConfig(config, moneyConfigYml + "/" + uuid + ".yml");
    }

    public void reloadMoney() {
        File[] files = getFiles(moneyConfigYml);
        for (File playerYml : files) {
            FileConfiguration config = getConfig(playerYml);
            UUID playerUUID = UUID.fromString(playerYml.getName().replace(".yml", ""));
            long playerMoney = config.getLong(this.money, 0);
            PlayerData.playerInfo.put(playerUUID, playerMoney);
        }
    }

    public void addRegion(String regionName, List<Double> positions) {
        FileConfiguration config = getConfig(regionConfigYml + "/" + regionName + ".yml");
        config.set(region, positions);
        saveConfig(config, regionConfigYml + "/" + regionName + ".yml");
    }

    public void getRegion() {
        File[] files = getFiles(regionConfigYml);
        for (File regionYml : files) {
            FileConfiguration config = getConfig(regionYml);
            String regionName = regionYml.getName().replace(".yml", "");
            List<Double> doubleList = config.getDoubleList(region);
            if (doubleList.size() < 6) {
                continue;
            }
            RegionInfo regionInfo = RegionInfo.builder()
                    .x1(doubleList.get(0))
                    .y1(doubleList.get(1))
                    .z1(doubleList.get(2))
                    .x2(doubleList.get(3))
                    .y2(doubleList.get(4))
                    .z2(doubleList.get(5))
                    .build();
            RegionData.regionData.put(regionName, regionInfo);
        }
    }

    private File[] getFiles(String path) {
        File regionFolder = new File(plugin.getDataFolder(), path);

        File[] files = regionFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) {
            messageModule.logInfo(BaseMessage.ERROR_CONFIG.getMessage());
            return new File[0];
        }
        return files;
    }
}

