package org.luddang.Event;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.luddang.Luddang;
import org.luddang.Module.Game.RegionModule;

// Event Listener 들을 등록하기 위해 만들어 졌습니다
public class EventManager {


    public EventManager(Server server, Luddang plugin) {
        server.getPluginManager().registerEvents(new PlayerEvent(plugin), plugin);
    }
}
