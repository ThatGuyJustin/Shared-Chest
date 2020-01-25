package com.github.thatguyjustin;

import com.github.thatguyjustin.commands.GetChest;
import com.github.thatguyjustin.listeners.GuiInventory;
import com.github.thatguyjustin.utils.Inv;
import com.github.thatguyjustin.utils.Logger;
import com.github.thatguyjustin.utils.MenuTasks;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class SurvivalPlugin extends JavaPlugin {

    private FileConfiguration config = this.getConfig();
    private GuiInventory handler = new GuiInventory(this, this.getDataFolder());

    @Override
    public void onEnable() {

        if(this.getConfig() == null){
            this.saveDefaultConfig();
        }

        Logger.info("&aPlugin Enabled!", true);
        getServer().getPluginManager().registerEvents(handler, this);
        getCommand("getchest").setExecutor(new GetChest());

    }

    @Override
    public void onDisable() {
        Logger.info("&dSaving Inventories!", true);
        HashMap<String, Inv> toSave = handler.getInventories();
        for(Inv inven: toSave.values())
        {
            inven.save();
        }
        Logger.info("&dInventories Saved!", true);
        Logger.info("&cPlugin Disabled!", true);
        MenuTasks.cancelAndRemoveTasks();
    }

    public FileConfiguration getConfig(){
        return this.config;
    }

    public SurvivalPlugin getInstance()
    {
        return this;
    }
}
