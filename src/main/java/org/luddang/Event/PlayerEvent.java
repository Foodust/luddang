package org.luddang.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.luddang.Luddang;
import org.luddang.Module.Game.RegionModule;

public class PlayerEvent implements Listener {

    private final RegionModule regionModule;

    public PlayerEvent(Luddang plugin) {
        this.regionModule = new RegionModule(plugin);
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        regionModule.isRegion(event);
    }
}
