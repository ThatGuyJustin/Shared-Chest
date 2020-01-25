package com.github.thatguyjustin.commands;

import com.github.thatguyjustin.utils.Logger;
import com.github.thatguyjustin.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GetChest implements CommandExecutor {

    ItemStack LoveChest = new ItemStack(Material.CHEST);
    ItemMeta m = LoveChest.getItemMeta();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;

        Player p = (Player) sender;

        if(!p.isOp())
        {
            p.sendMessage(StringUtils.color("&cYou do not have permission to use this command!"));
            return false;
        }

        m.setUnbreakable(true);
        m.setDisplayName(StringUtils.color("&5&lThe &oLove &5&lChest"));
        m.addEnchant(Enchantment.MENDING, 1, true);
        LoveChest.setItemMeta(m);

        p.getInventory().addItem(LoveChest);

        return false;
    }

}
