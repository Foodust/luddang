package org.luddang.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.luddang.Data.RegionData;
import org.luddang.Message.BaseMessage;

import java.util.*;
import java.util.stream.Collectors;

public class CommandSub implements TabCompleter {


    Set<String> mainSub = EnumSet.range(BaseMessage.COMMAND_MONEY, BaseMessage.COMMAND_REGION).stream().map(BaseMessage::getMessage).collect(Collectors.toSet());
    Set<String> moneySub = EnumSet.range(BaseMessage.COMMAND_SET, BaseMessage.COMMAND_SET).stream().map(BaseMessage::getMessage).collect(Collectors.toSet());
    Set<String> regionSub = EnumSet.range(BaseMessage.COMMAND_ADD, BaseMessage.COMMAND_ADD).stream().map(BaseMessage::getMessage).collect(Collectors.toSet());

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], mainSub, completions);
        } else if (args.length == 2) {
            switch (BaseMessage.getByMessage(args[0])) {
                case COMMAND_MONEY -> StringUtil.copyPartialMatches(args[1], moneySub, completions);
                case COMMAND_REGION -> StringUtil.copyPartialMatches(args[1], regionSub, completions);
            }
        } else if (args.length == 3) {
            switch (BaseMessage.getByMessage(args[0])) {
                case COMMAND_MONEY ->
                        StringUtil.copyPartialMatches(args[2], Bukkit.getOnlinePlayers().stream().map(Player::getName).toList(), completions);
//                case COMMAND_REGION ->
//                        StringUtil.copyPartialMatches(args[1], RegionData.regionData.entrySet().stream().findAny().stream().map(Map.Entry::getKey).toList(), completions);
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
