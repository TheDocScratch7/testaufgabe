package net.ledaria.listener;

import net.ledaria.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class PlayerDestroyHoneyBlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHoneyBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.HONEY_BLOCK) {
            if (!event.getBlock().hasMetadata("type"))
                return;
            event.setDropItems(false);
            String type = event.getBlock().getMetadata("type").get(0).asString();
            int zufall = (int) Math.floor((Math.random() * 1000) % 100); //0 - 99
            if (type.equals("BAD")) { // 90% BAD // 9% OKAY // 1% GOOD
                if (zufall < 25)
                    openBlock("DEADLY", event.getBlock().getLocation());
                else if (zufall < 70)
                    openBlock("BAD", event.getBlock().getLocation());
                else if (zufall < 90)
                    openBlock("OKAY", event.getBlock().getLocation());
                else if (zufall < 99)
                    openBlock("GOOD", event.getBlock().getLocation());
                else
                    openBlock("VERY_GOOD", event.getBlock().getLocation());
            } else if (type.equals("GOOD")) { // 1% BAD // 9% OKAY // 90% GOOD
                if (zufall < 5)
                    openBlock("DEADLY", event.getBlock().getLocation());
                else if (zufall < 15)
                    openBlock("BAD", event.getBlock().getLocation());
                else if (zufall < 35)
                    openBlock("OKAY", event.getBlock().getLocation());
                else if (zufall < 95)
                    openBlock("GOOD", event.getBlock().getLocation());
                else
                    openBlock("VERY_GOOD", event.getBlock().getLocation());
            } else {
                if (zufall < 20)
                    openBlock("DEADLY", event.getBlock().getLocation());
                else if (zufall < 40)
                    openBlock("BAD", event.getBlock().getLocation());
                else if (zufall < 60)
                    openBlock("OKAY", event.getBlock().getLocation());
                else if (zufall < 80)
                    openBlock("GOOD", event.getBlock().getLocation());
                else
                    openBlock("VERY_GOOD", event.getBlock().getLocation());
            }
        }
    }

    protected void openBlock(String type, Location loc) {
        List<String> itemsInside = ItemManager.items.get(type);
        Collections.shuffle(itemsInside);
        String itemInsideToDrop = itemsInside.get(0);
        ItemStack item = ItemManager.stringToItem(itemInsideToDrop);
        if (item == null)
            return;
        if (item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().getDisplayName().startsWith("/")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item.getItemMeta().getDisplayName().substring(1));
            return;
        }
        loc.getWorld().dropItem(loc, item);
    }
}
