package org.luddang.Timer;

import org.bukkit.entity.Player;
import org.luddang.Data.RegionData;
import org.luddang.Luddang;
import org.luddang.Module.Base.MessageModule;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

public class ActionBarTimer extends TimerTask {
    private final MessageModule messageModule;

    public ActionBarTimer(Luddang plugin) {
        this.messageModule = new MessageModule(plugin);
    }

    @Override
    public void run() {
        try {
            checkingCoolTime();
        } catch (Exception ignore) {
        }
    }

    public void checkingCoolTime() {
        if (RegionData.nowRegion.isEmpty()) return;

        for (Map.Entry<Player, Set<String>> entry : RegionData.nowRegion.entrySet()) {
            Player player = entry.getKey();
            Set<String> regions = entry.getValue();
            StringBuilder result = new StringBuilder();
            for (String region : regions) {
                result.append(region).append(",");
            }
            result.deleteCharAt(result.length() - 1);
            messageModule.sendPlayerActionBar(player, result.toString());
        }
    }
}
