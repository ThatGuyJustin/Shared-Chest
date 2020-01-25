package com.github.thatguyjustin.utils;

import com.github.thatguyjustin.SurvivalPlugin;
import com.github.thatguyjustin.config.Config;
import com.github.thatguyjustin.listeners.GuiInventory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GrimReaper52498 on 4/23/2016.
 *
 * @author GrimReaper52498 (Tyler Brady) [Modified by ThatGuyJustin]
 */

public class Inv {

    private Inventory inventory;
    private SurvivalPlugin pl;
    private String team;
    private File dir;
    private FileConfiguration config;

    public Inv(Inventory inventory, String team, SurvivalPlugin pl) {
        this.inventory = inventory;
        this.team = team;
        this.pl = pl;
        this.dir = new File(this.pl.getDataFolder(), "/data/");
        config = Config.getInstance().getConfig(pl, dir, this.team);
    }

    public void save() {

        ItemStack[] items = Arrays.copyOf(inventory.getContents(), inventory.getContents().length);

        config.set("Inventory", items);

        saveFile();

    }

    public void load() {
        Object a = config.get("Inventory");

        if (a == null /* || b == null */) {
            return;
        }
        ItemStack[] inventory = null;

        if (a instanceof ItemStack[]) {
            inventory = (ItemStack[]) a;
        } else if (a instanceof List) {
            List lista = (List) a;
            inventory = (ItemStack[]) lista.toArray(new ItemStack[0]);
        }



        this.inventory.clear();
        this.inventory.setContents(inventory);

    }

    private void saveFile() {
        Config.getInstance().saveConfig(pl, dir, this.team);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
