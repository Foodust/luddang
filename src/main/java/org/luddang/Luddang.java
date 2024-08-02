package org.luddang;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.luddang.Command.CommandManager;
import org.luddang.Data.TaskData;
import org.luddang.Data.TickData;
import org.luddang.Event.EventManager;
import org.luddang.Message.BaseMessage;
import org.luddang.Module.Base.ConfigModule;
import org.luddang.Module.Base.TaskModule;
import org.luddang.Timer.MoneyTimer;
import org.luddang.Timer.RegionMessageTimer;

import java.util.logging.Logger;

@Getter
public final class Luddang extends JavaPlugin implements PluginMessageListener {
    private BukkitAudiences adventure;
    private Main main;
    private Plugin plugin;
    private Logger log;
    private ConfigModule configModule;

    public @NonNull BukkitAudiences getAdventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.main = getMain();
        this.log = Bukkit.getLogger();
        this.adventure = BukkitAudiences.create(this);
        this.plugin = this;
        this.configModule = new ConfigModule(this);
        CommandManager commandManager = new CommandManager(this);
        EventManager eventManager = new EventManager(this.getServer(), this);

        configModule.initialize();

        this.getServer().getMessenger().registerIncomingPluginChannel(this, BaseMessage.CHANNEL_NAME.getMessage(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, BaseMessage.CHANNEL_NAME.getMessage());

        TaskModule taskModule = new TaskModule(this);
        TaskData.tasks.add(taskModule.runBukkitTaskTimer(new MoneyTimer(this), TickData.longTick, 5 * TickData.longTick));
        TaskData.tasks.add(taskModule.runBukkitTaskTimer(new RegionMessageTimer(this),TickData.longTick, TickData.longTick / 2));
    }

    @Override
    public void onDisable() {
        for (String channel : getServer().getMessenger().getIncomingChannels(this)) {
            getServer().getMessenger().unregisterIncomingPluginChannel(this, channel);
        }
        for (String channel : getServer().getMessenger().getOutgoingChannels(this)) {
            getServer().getMessenger().unregisterOutgoingPluginChannel(this, channel);
        }
        // Plugin shutdown logic
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        configModule.release();
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] messages) {
        Bukkit.broadcastMessage("들어옴");
        if (!channel.equals("luddang")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(messages);
        String subChannel = input.readUTF();
        if(subChannel.equals("luddang")) {
            Bukkit.broadcastMessage("들어옴");
        }
    }
}
