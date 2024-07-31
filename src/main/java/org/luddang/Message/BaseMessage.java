package org.luddang.Message;

import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum BaseMessage {

    // prefix
    PREFIX(""),
    PREFIX_C(""),

    // channel
    CHANNEL_NAME("luddang:luddang"),

    // command
    COMMAND_LD("ld"),
    COMMAND_MONEY("money"),
    COMMAND_REGION("region"),
    COMMAND_ADD("add"),
    COMMAND_SET("set"),

    // info
    INFO_SET_MONEY("<aqua>돈이 설정되었습니다.</aqua>"),
    INFO_ADD_REGION("<aqua>지역이 설정되었습니다.</aqua>"),

    // 기본
    DEFAULT("기본"),
        // Error
    ERROR("에러"),
    ERROR_COMMAND("<dark_red>잘못된 명령어입니다.</dark_red>"),
    ERROR_DOES_NOT_EXITS_PLAYER("<dark_red>플레이어가 존재하지 않습니다.</dark_red>"),
    ERROR_VALUE_UNDER_INTEGER("<dark_red>값은 0 이상이어야 합니다.</dark_red>"),

    ERROR_CONFIG("콘피그에서 에러가 발생함"),
    ;

    private final String message;

    BaseMessage(String message) {
        this.message = message;
    }

    private static final Map<String, BaseMessage> commandInfo = new HashMap<>();

    static {
        for (BaseMessage baseMessage : EnumSet.range(COMMAND_MONEY, COMMAND_SET)) {
            commandInfo.put(baseMessage.message, baseMessage);
        }
    }

    public static BaseMessage getByMessage(String message) {
        return commandInfo.getOrDefault(message,ERROR);
    }
}
