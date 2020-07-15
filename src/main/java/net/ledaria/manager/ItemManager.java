package net.ledaria.manager;

import com.google.common.base.Charsets;
import net.ledaria.database.DBController;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    public volatile static Map<String, List<String>> items = new HashMap<>();

    public void start() {
        long start = System.currentTimeMillis();
        new DBController().initDBConnection();
        items = new DBController().loadItems();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Items loaded took " + (System.currentTimeMillis() - start) + "ms");
        System.out.println(items);
    }

    public static void addItem(ItemStack stack, String type) {
        String itemStr = itemToString(stack);
        new DBController().initDBConnection();
        new DBController().addItem(type, itemStr);
        List<String> itemsInside = items.get(type);
        itemsInside.add(itemStr);
        items.put(type, itemsInside);
    }

    protected static String itemToString(ItemStack itemStack) {
        YamlConfiguration itemConfig = new YamlConfiguration();
        itemConfig.set("item", itemStack);
        String serialized = itemConfig.saveToString();
        serialized = new String(Base64.getEncoder().encode(serialized.getBytes(StandardCharsets.UTF_8)), Charsets.UTF_8);
        return serialized;
    }

    public static ItemStack stringToItem(String string) {
        String yaml = new String(Base64.getDecoder().decode(string.getBytes(StandardCharsets.UTF_8)), Charsets.UTF_8);
        YamlConfiguration itemConfig = new YamlConfiguration();
        try {
            itemConfig.loadFromString(yaml);
            return itemConfig.getItemStack("item");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
