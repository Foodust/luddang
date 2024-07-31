package org.luddang.Data;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TaskData {
    public static List<BukkitTask> tasks = new ArrayList<>();
    public static void release(){
        tasks.forEach(bukkitTask -> {
            Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
        });
    }
}
