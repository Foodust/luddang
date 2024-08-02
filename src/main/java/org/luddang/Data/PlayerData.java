package org.luddang.Data;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    public static HashMap<String, Long> playerInfo = new HashMap<>();
    public static void release(){
        playerInfo.clear();
    }
}
