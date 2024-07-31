package org.luddang.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.luddang.Luddang;
import org.luddang.Message.BaseMessage;
import org.luddang.Module.Game.MoneyModule;
import org.luddang.Module.Game.RegionModule;

import java.util.Objects;

// 커맨드 를 할 수 있게 해줍니다!
public class CommandManager implements CommandExecutor {
    private final MoneyModule moneyModule;
    private final RegionModule regionModule;

    public CommandManager(Luddang plugin) {
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_LD.getMessage())).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_LD.getMessage())).setTabCompleter(new CommandSub());
        this.moneyModule = new MoneyModule(plugin);
        this.regionModule = new RegionModule(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] data) {
        BaseMessage subCommand = BaseMessage.getByMessage(data[0]);
        switch (subCommand) {
            case COMMAND_MONEY -> moneyModule.setMoney(sender, data);
            case COMMAND_REGION -> regionModule.addRegion(sender, data);
        }
        return true;
    }
}
