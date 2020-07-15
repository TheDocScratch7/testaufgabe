package net.ledaria.commands;

import net.ledaria.Testaufgabe;
import net.ledaria.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.stream.Stream;

public class TestaufgabeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender)
            return false;
        Player player = (Player) sender;
        if (args.length == 6) {
            if (player.getItemInHand().getType() == Material.AIR)
                return false;
            if (args[0].equals("add") && args[1].equals("hand") && args[2].equals("item") && args[3].equals("to") && args[4].equals("db")) {
                if (Stream.of(Testaufgabe.type).anyMatch(s -> s.equals(args[5]))) {
                    ItemManager.addItem(player.getItemInHand(), args[5]);
                    player.sendMessage(Testaufgabe.prefix + "§aItem hinzugefügt!");
                    return true;
                }
            }
        } else if (args.length == 3) {
            if (args[0].equals("give") && args[1].equals("luckyblock")) {
                if (Stream.of(Testaufgabe.type).anyMatch(s -> s.equals(args[2]))) {
                    ItemStack itemStack = new ItemStack(Material.HONEY_BLOCK);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.getPersistentDataContainer()
                            .set(new NamespacedKey(Testaufgabe.testaufgabe, "type"),
                                    PersistentDataType.PrimitivePersistentDataType.STRING, args[2]);
                    if (args[2].equals("BAD")) {
                        meta.setLore(Arrays.asList("Schlecht"));
                    } else if (args[2].equalsIgnoreCase("GOOD")) {
                        meta.setLore(Arrays.asList("Gut"));
                    } else {
                        meta.setLore(Arrays.asList("Okay"));
                    }
                    meta.setDisplayName("§6Lucky§eBlock");
                    itemStack.setItemMeta(meta);
                    player.setItemInHand(itemStack);
                    return true;
                }
            }
        }
        return false;
    }

}
