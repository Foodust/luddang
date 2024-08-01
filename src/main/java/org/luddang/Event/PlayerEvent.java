package org.luddang.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.luddang.Luddang;
import org.luddang.Module.Game.MoneyModule;
import org.luddang.Module.Game.RegionModule;

public class PlayerEvent implements Listener {

    private final RegionModule regionModule;
    private final MoneyModule moneyModule;

    public PlayerEvent(Luddang plugin) {
        this.regionModule = new RegionModule(plugin);
        this.moneyModule = new MoneyModule(plugin);
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        regionModule.isRegion(event);
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        moneyModule.isJoining(event);
    }
}
