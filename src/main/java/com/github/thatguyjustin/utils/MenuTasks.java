package com.github.thatguyjustin.utils;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.scheduler.BukkitTask;

public class MenuTasks {

    private static HashMap<String, BukkitTask> tasks = Maps.newHashMap();


    public static void addTask(String name, BukkitTask task) {
        if (tasks.containsKey(name)) {
            tasks.get(name).cancel();
        }

        tasks.put(name, task);
    }

    public static void removeTask(String name) {
        if (tasks.containsKey(name)) {
            tasks.get(name).cancel();
            tasks.remove(name);
        }

    }


    public static void cancelAndRemoveTasks() {
        Set<String> names = tasks.keySet();
        for (String name : names) {
            BukkitTask task = tasks.get(name);
            task.cancel();
            tasks.remove(name);
        }
    }

}
