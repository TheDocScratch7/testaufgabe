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

public class playerDestroyHoneyBlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHoneyBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.HONEY_BLOCK) {
            if (!event.getBlock().hasMetadata("warsch"))
                return;
            event.setDropItems(false);
            String warsch = event.getBlock().getMetadata("warsch").get(0).asString();
            int zufall = (int) Math.floor((Math.random() * 1000) % 100); //0 - 99
            if (warsch.equals("BAD"))
            { // 90% BAD // 9% OKAY // 1% GOOD
                if (zufall < 90)
                    openBlock("BAD", event.getBlock().getLocation());
                else if (zufall < 99)
                    openBlock("OKAY", event.getBlock().getLocation());
                else
                    openBlock("GOOD", event.getBlock().getLocation());
            }
            else if (warsch.equals("GOOD"))
            { // 1% BAD // 9% OKAY // 90% GOOD
                if (zufall < 90)
                    openBlock("GOOD", event.getBlock().getLocation());
                else if (zufall < 99)
                    openBlock("OKAY", event.getBlock().getLocation());
                else
                    openBlock("BAD", event.getBlock().getLocation());
            }else{
                if (zufall < 33)
                    openBlock("GOOD", event.getBlock().getLocation());
                else if (zufall < 66) {
                    openBlock("OKAY", event.getBlock().getLocation());
                } else
                    openBlock("BAD", event.getBlock().getLocation());
            }
        }
    }

    protected void openBlock(String warsch, Location loc) {
        List<String> itemsInside = itemManager.items.get(warsch);
        Collections.shuffle(itemsInside);
        String itemInsideToDrop = itemsInside.get(0);
        ItemStack item = itemManager.stringToItem(itemInsideToDrop);
        if (item == null)
            return;
        if (item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().getDisplayName().startsWith("/")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item.getItemMeta().getDisplayName().substring(1));
            return;
        }
        loc.getWorld().dropItem(loc, item);
    }
}
