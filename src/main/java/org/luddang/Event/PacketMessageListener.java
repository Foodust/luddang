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
    public void onPluginMessageReceived(@NotNull String var1, @NotNull Player var2, @NotNull byte[] var3) {
        messageModule.broadcastMessage(var1);
        messageModule.broadcastMessage(var2.getName());
        messageModule.broadcastMessage(Arrays.toString(var3));
    }
}
