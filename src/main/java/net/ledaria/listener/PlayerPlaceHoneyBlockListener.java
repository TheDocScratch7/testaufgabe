package net.ledaria.listener;

import net.ledaria.Testaufgabe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

public class PlayerPlaceHoneyBlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlaceHoneyBlock(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.HONEY_BLOCK) {
            if (event.getItemInHand().hasItemMeta()) {
                if (event.getItemInHand().getItemMeta().getPersistentDataContainer()
                        .has(new NamespacedKey(Testaufgabe.testaufgabe, "type"),
                                PersistentDataType.PrimitivePersistentDataType.STRING)) {
                    String type = event
                            .getItemInHand()
                            .getItemMeta()
                            .getPersistentDataContainer()
                            .get(new NamespacedKey(Testaufgabe.testaufgabe, "type"),
                                    PersistentDataType.PrimitivePersistentDataType.STRING);
                    event.getBlock().setMetadata("type", new FixedMetadataValue(Testaufgabe.testaufgabe, type));
                }
            }
        }
    }
}
