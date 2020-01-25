package com.github.thatguyjustin.listeners;

import com.github.thatguyjustin.SurvivalPlugin;
import com.github.thatguyjustin.utils.Inv;
import com.github.thatguyjustin.utils.Logger;
import com.github.thatguyjustin.utils.MenuTasks;
import com.github.thatguyjustin.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Chest;

import java.io.File;
import java.util.*;

public class GuiInventory implements InventoryHolder, Listener {

    private HashMap<String, Inv> shared = new HashMap<String, Inv>();
    String[] TeamJam = {"84b5c07d-2709-44d7-a9eb-32945986fbd5", "184770a4-e865-444e-8607-283430e267f2"};
    String[] TeamAppleJeff = {"28e86e29-ffbe-4f4e-b384-5cc5a89e4e0c", "b2dd25f1-4eec-47f7-bc92-e2ccd467f75a"};
    private SurvivalPlugin pl;

    public GuiInventory(SurvivalPlugin plugin, File dataDir)
    {
        this.pl = plugin;
        Logger.info("&dLoading Inventories!", true);
        this.createInventory("TeamJam");
        this.createInventory("TeamAppleJeff");
        Logger.info("&dInventories Loaded!", true);
    }

    private void createInventory(String team)
    {
        Inventory tmp = Bukkit.createInventory(this, 54, StringUtils.color("&5&lThe &oLove &5&lChest"));
        Inv inven = new Inv(tmp, team, this.pl);
        inven.load();
        shared.put(team, inven);
    }

    @NotNull
    @Override
    public Inventory getInventory()
    {
        return null;
    }

    @NotNull
    public Inventory getInventory(Player p) {
        UUID id = p.getUniqueId();
        String uuid = id.toString();
        String team = "";
        if(TeamJam[1].equals(uuid) || TeamJam[0].equals(uuid))
        {
            team = "TeamJam";
        }else{
            team = "TeamAppleJeff";
        }
        Inventory toReturn = initializeItems(team, shared.get(team).getInventory());
        return toReturn;
    }

    public Inventory initializeItems(String team, Inventory inv) {
        ItemStack g1 = createGuiItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ");
        ItemStack g2 = createGuiItem(Material.CYAN_STAINED_GLASS_PANE, " ");
        ItemStack g3 = createGuiItem(Material.LIME_STAINED_GLASS_PANE, " ");
        String n1, n2;
        OfflinePlayer p1, p2;
        if(team.equals("TeamJam"))
        {
            p1 = this.pl.getServer().getOfflinePlayer("McIsTheLimit");
            n1 = "Justin";
            p2 = this.pl.getServer().getOfflinePlayer("SamBukka13");
            n2 = "Sam";
        }else{
            p1 = this.pl.getServer().getOfflinePlayer("Hacker_jeff");
            n1 = "August";
            p2 = this.pl.getServer().getOfflinePlayer("AppleLilly");
            n2 = "Lilly";
        }
        String[] l1 = getLore(p1);
        String[] l2 = getLore(p2);
        ItemStack Skull1 = createGuiItem(Material.PLAYER_HEAD, n1, StringUtils.color(l1[0]), StringUtils.color(l1[1]), StringUtils.color(l1[2]));
            SkullMeta meta = (SkullMeta) Skull1.getItemMeta();
            meta.setOwner(p1.getName());
            Skull1.setItemMeta(meta);
        ItemStack Skull2 = createGuiItem(Material.PLAYER_HEAD, n2, StringUtils.color(l2[0]), StringUtils.color(l2[1]), StringUtils.color(l2[2]));
            SkullMeta meta2 = (SkullMeta) Skull2.getItemMeta();
            meta2.setOwner(p2.getName());
            Skull2.setItemMeta(meta2);
        inv.setItem(12, Skull1);
        inv.setItem(14, Skull2);
        int[] glassSet = {10, 11, 13, 15 ,16};
        for(int i: glassSet)
        {
            inv.setItem(i, g3);
        }
        for(int i = 0; i < 9; i += 2)
        {
            inv.setItem(i, g1);
            inv.setItem(i + 1, g2);
        }
        inv.setItem(9, g2);
        inv.setItem(17, g2);
        for(int i = 18; i < 26; i += 2)
        {
            inv.setItem(i, g1);
            inv.setItem(i + 1, g2);
        }
        inv.setItem(26, g1);
        return inv;
    }

    private String[] getLore(OfflinePlayer p)
    {
        String[] lore = new String[3];
        if(p.isOnline()) {
            Location l = p.getPlayer().getLocation();
            lore[0] = "&b&lLocation&8: &aX&8: &e" + l.getBlockX() + " &aY&8: &e" + l.getBlockY() + " &aZ&8: &e" + l.getBlockZ();
            lore[1] = "&b&lHealth&8: &a" + p.getPlayer().getHealth() + "/20";
            lore[2] = "&b&lFood&8: &a" + p.getPlayer().getFoodLevel() + "/20";
        }else{
            lore[0] = "&b&lLocation&8: &aN/A";
            lore[1] = "&b&lHealth&8: &aN/A";
            lore[2] = "&b&lFood&8: &aN/A";
        }
        return lore;
    }

    private ItemStack createGuiItem(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {

            metalore.add(lorecomments);

        }

        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    public void openInventory(Player p) {
        final Player ree = p;
        MenuTasks.addTask(p.getName(), (new BukkitRunnable()  {
            public void run() {
                final Inventory inven = getInventory(ree);
            }
        }.runTaskTimerAsynchronously(pl.getInstance(), 0L, 10L)));
        ree.openInventory(getInventory(ree));
    }

    @EventHandler
    private void onRightClick(PlayerInteractEvent event)
    {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {

            if(event.getItem() == null) return;
            if(event.getItem().getType() == Material.CHEST) {
                if(event.getItem().getItemMeta().getDisplayName() == null) return;
                if(event.getItem().getItemMeta().getDisplayName().equals(StringUtils.color("&5&lThe &oLove &5&lChest"))) {
                    openInventory(event.getPlayer());
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    private void onIventoryInteract(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == shared.get("TeamJam").getInventory() || event.getClickedInventory() == shared.get("TeamAppleJeff").getInventory())
        {
            if(event.getSlot() <= 26) event.setCancelled(true);
        }
    }

    public HashMap<String, Inv> getInventories()
    {
        return this.shared;
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event)
    {
        if(event.getInventory() == shared.get("TeamJam").getInventory() || event.getInventory() == shared.get("TeamAppleJeff").getInventory())
        {
            MenuTasks.removeTask(event.getPlayer().getName());
        }
    }
}