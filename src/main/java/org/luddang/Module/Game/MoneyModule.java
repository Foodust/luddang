package org.luddang.Module.Game;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.luddang.Data.PlayerData;
import org.luddang.Luddang;
import org.luddang.Message.BaseMessage;
import org.luddang.Module.Base.ConfigModule;
import org.luddang.Module.Base.MessageModule;

public class MoneyModule {

    private final Luddang plugin;
    private final MessageModule messageModule;
    private final ConfigModule configModule;

    public MoneyModule(Luddang plugin) {
        this.plugin = plugin;
        this.configModule = new ConfigModule(plugin);
        this.messageModule = new MessageModule(plugin);
    }

    public void setMoney(CommandSender sender, String[] data) {
        if (data.length < 4 || !(data[1].equals(BaseMessage.COMMAND_SET.getMessage()))) {
            messageModule.sendPlayerC(sender, BaseMessage.ERROR_COMMAND.getMessage());
            return;
        }

        Player player = Bukkit.getPlayer(data[2]);
        if (player == null) {
            messageModule.sendPlayerC(sender, BaseMessage.ERROR_DOES_NOT_EXITS_PLAYER.getMessage());
            return;
        }

        long moneyValue = Long.parseLong(data[3]);
        if (moneyValue < 0) {
            messageModule.sendPlayerC(sender, BaseMessage.ERROR_VALUE_UNDER_INTEGER.getMessage());
            return;
        }
        configModule.addMoney(player.getName(), moneyValue);
        configModule.reloadMoney();
        sendMoney(player, moneyValue);
        messageModule.sendPlayerC(sender, BaseMessage.INFO_SET_MONEY.getMessage());
    }

    public Boolean isInChannel(Player player) {
        return player.getListeningPluginChannels().contains(BaseMessage.CHANNEL_NAME.getMessage());
    }

    public void registerChannel(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.addChannel(BaseMessage.CHANNEL_NAME.getMessage());
    }

    public void sendMoney(Player player, Long money) {
        if (!isInChannel(player)) registerChannel(player);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("money");
        out.writeUTF(player.getName());
        out.writeLong(money);

        player.sendPluginMessage(plugin, BaseMessage.CHANNEL_NAME.getMessage(), out.toByteArray());
    }

    public void isJoining(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (!PlayerData.playerInfo.containsKey(playerName)) return;
        Long moneyValue = PlayerData.playerInfo.get(playerName);
        sendMoney(player, moneyValue);
    }
}
