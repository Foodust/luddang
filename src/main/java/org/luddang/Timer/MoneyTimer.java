package org.luddang.Timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luddang.Data.PlayerData;
import org.luddang.Luddang;
import org.luddang.Module.Game.MoneyModule;

import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;

public class MoneyTimer extends TimerTask {

    private final Luddang plugin;
    private final MoneyModule moneyModule;

    public MoneyTimer(Luddang plugin) {
        this.plugin = plugin;
        this.moneyModule = new MoneyModule(plugin);
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("돈이 전달됨");
        for (Map.Entry<String, Long> entry : PlayerData.playerInfo.entrySet()) {
            String playerName = entry.getKey();
            Long money = entry.getValue();
            Player player = Bukkit.getPlayerExact(playerName);
            if (player == null) continue;
            moneyModule.sendMoney(player, money);
            player.sendMessage("플레이어에게 돈이 전달됨");
        }

    }
}
