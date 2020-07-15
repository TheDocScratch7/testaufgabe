import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

public class playerPlaceHoneyBlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlaceHoneyBlock(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.HONEY_BLOCK) {
            if (event.getItemInHand().hasItemMeta())
            {
                if (event.getItemInHand().getItemMeta().getPersistentDataContainer()
                        .has(new NamespacedKey(testaufgabe.testaufgabe, "warsch"),
                                PersistentDataType.PrimitivePersistentDataType.STRING))
                {
                    String warsch = event
                            .getItemInHand()
                            .getItemMeta()
                            .getPersistentDataContainer()
                            .get(new NamespacedKey(testaufgabe.testaufgabe, "warsch"),
                                    PersistentDataType.PrimitivePersistentDataType.STRING);
                    event.getBlock().setMetadata("warsch", new FixedMetadataValue(testaufgabe.testaufgabe, warsch));
                }
            }
        }
    }
}
