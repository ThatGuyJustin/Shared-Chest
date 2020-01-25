package com.github.thatguyjustin.config;

import com.github.thatguyjustin.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by GrimReaper52498 on 4/23/2016.
 *
 * @author GrimReaper52498 (Tyler Brady)
 */
public class Config {
    private static Config instance = null;
    private JavaPlugin plugin = null;
    private HashMap<JavaPlugin, HashMap<String, HashMap<String, FileConfiguration>>> configs =
            new HashMap<>();

    private Config() {
    }

    /**
     * Get the Config instance
     *
     * @return this
     */
    public static Config getInstance() {
        if (instance == null) {
            return (instance = new Config());
        }

        return instance;
    }

    /**
     * Reload the specified config, from the given Plugin
     *
     * @param plugin    Plugin in which the config is part of
     * @param directory Directory where the file is stored
     * @param id        Name of the config file
     * @return FileConfiguration of the file
     */
    public FileConfiguration reloadConfig(JavaPlugin plugin, File directory, String id) {
        if (!configs.containsKey(plugin)) {
            HashMap<String, HashMap<String, FileConfiguration>> map = new HashMap<>();
            configs.put(plugin, map);
        }
        File customConfigFile;

        if (directory.equals(plugin.getDataFolder())) {
            customConfigFile = new File(plugin.getDataFolder(), id + ".yml");
        } else {
            customConfigFile = new File(plugin.getDataFolder(), File.separator + "/" + directory.getName() + "/" + File.separator + id + " .yml");
        }

        FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        File defConfigStream = new File(plugin.getDataFolder(), id + ".yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }

        if (directory.equals(plugin.getDataFolder())) {
            if (configs.get(plugin).containsKey(plugin.getDataFolder().getName())) {
                configs.get(plugin).get(plugin.getDataFolder().getName()).put(id, customConfig);
            } else {
                HashMap<String, FileConfiguration> map = new HashMap<>();
                map.put(id, customConfig);
                configs.get(plugin).put(plugin.getDataFolder().getName(), map);

            }
        } else {
            if (configs.get(plugin).containsKey(directory.getName())) {
                configs.get(plugin).get(directory.getName()).put(id, customConfig);
            } else {
                HashMap<String, FileConfiguration> map = new HashMap<>();
                map.put(id, customConfig);
                configs.get(plugin).put(directory.getName(), map);

            }
        }
        return customConfig;
    }

    public HashMap<String, FileConfiguration> getConfigsFromDirectory(JavaPlugin plugin, File dir) {
        if (configs.containsKey(plugin) && configs.get(plugin).containsKey(dir.getName())) {
            return configs.get(plugin).get(dir);
        }
        return null;
    }

    /**
     * Retrieve the specified config
     *
     * @param plugin    Plugin in which the config is part of
     * @param directory Directory where the file is stored
     * @param id        Name of the config file
     * @return FileConfiguration of the file
     */
    public FileConfiguration getConfig(JavaPlugin plugin, File directory, String id) {
        if (configs.containsKey(plugin) && configs.get(plugin).containsKey(directory.getName()) && configs.get(plugin).get(directory.getName()).containsKey(id)) {
            return configs.get(plugin).get(directory.getName()).get(id);
        }
        return reloadConfig(plugin, directory, id);
    }

    /**
     * Save the specified config
     *
     * @param plugin    Plugin in which the config is part of
     * @param directory Directory where the file is stored
     * @param id        Name of the config file
     */
    public void saveConfig(JavaPlugin plugin, File directory, String id) {
        try {
            File customConfigFile;
            if (directory.equals(plugin.getDataFolder())) {
                customConfigFile = new File(plugin.getDataFolder(), id + ".yml");
            } else {
                customConfigFile = new File(plugin.getDataFolder(), File.separator + "/" + directory.getName() + "/" + File.separator + id + " .yml");
            }
            getConfig(plugin, directory, id).save(customConfigFile);
        } catch (Exception ex) {
        }
    }

    /**
     * Check whether or not the file exists
     *
     * @param plugin    Plugin in which the config is part of
     * @param directory Directory where the file is stored
     * @param id        Name of the config file
     */
    public boolean exists(JavaPlugin plugin, File directory, String id) {
        File customConfigFile;
        if (directory.equals(plugin.getDataFolder())) {
            customConfigFile = new File(plugin.getDataFolder(), id + ".yml");
        } else {
            customConfigFile = new File(plugin.getDataFolder(), File.separator + directory.getName() + File.separator + id + " .yml");
        }
        return customConfigFile.exists();
    }

}
