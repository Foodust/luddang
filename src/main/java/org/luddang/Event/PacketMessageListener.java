package org.luddang.Event;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.luddang.Luddang;
import org.luddang.Module.Base.MessageModule;

import java.util.Arrays;

public class PacketMessageListener implements PluginMessageListener {

    private final MessageModule messageModule;

    public PacketMessageListener(Luddang plugin) {
        this.messageModule = new MessageModule(plugin);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String string, @NotNull Player player, @NotNull byte[] bytes) {
        messageModule.broadcastMessage(string);
        messageModule.broadcastMessage(player.getName());
        messageModule.broadcastMessage(Arrays.toString(bytes));
    }
}
