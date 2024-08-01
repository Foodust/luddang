package org.luddang;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.luddang.Command.CommandManager;
import org.luddang.Data.TaskData;
import org.luddang.Data.TickData;
import org.luddang.Event.EventManager;
import org.luddang.Event.PacketMessageListener;
import org.luddang.Message.BaseMessage;
import org.luddang.Module.Base.ConfigModule;
import org.luddang.Module.Base.TaskModule;
import org.luddang.Timer.MoneyTimer;
import org.luddang.Timer.RegionMessageTimer;

import java.util.logging.Logger;

@Getter
public final class Luddang extends JavaPlugin {
    private BukkitAudiences adventure;
    private PluginMessageListenerRegistration pluginListener;
    private Plugin plugin;
    private Logger log;
    private ConfigModule configModule;
    private PacketMessageListener packetMessageListener;

    public @NonNull BukkitAudiences getAdventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.log = Bukkit.getLogger();
        this.adventure = BukkitAudiences.create(this);
        this.plugin = this;
        this.configModule = new ConfigModule(this);
        this.packetMessageListener = new PacketMessageListener(this);
        CommandManager commandManager = new CommandManager(this);
        EventManager eventManager = new EventManager(this.getServer(), this);

        configModule.initialize();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, BaseMessage.CHANNEL_NAME.getMessage());
        pluginListener = this.getServer().getMessenger().registerIncomingPluginChannel(this, BaseMessage.CHANNEL_NAME.getMessage(), packetMessageListener);

        TaskModule taskModule = new TaskModule(this);
        TaskData.tasks.add(taskModule.runBukkitTaskTimer(new MoneyTimer(this), 0L, 60 * TickData.longTick));
        TaskData.tasks.add(taskModule.runBukkitTaskTimer(new RegionMessageTimer(this), 0L, TickData.longTick / 2));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        configModule.release();
        pluginListener = null;
    }
}
