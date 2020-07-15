package net.ledaria;

import net.ledaria.commands.TestaufgabeCommand;
import net.ledaria.database.DBController;
import net.ledaria.listener.PlayerDestroyHoneyBlockListener;
import net.ledaria.listener.PlayerPlaceHoneyBlockListener;
import net.ledaria.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

public class Testaufgabe extends TestaufgabeJP implements CommandExecutor {

    public static volatile String[] type = new String[]{"BAD", "OKAY", "GOOD"};
    public static volatile String prefix = new String("§0Testaufgabe -> §7");
    public static volatile Testaufgabe testaufgabe;

    public static void main(String[] args) {
        new Testaufgabe();
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin aktiviert");
        new DBController().closeDBConnection();
    }

    @Override
    public void onEnable() {
        testaufgabe = this;
        System.out.println("Plugin aktiviert");
        getCommand("testaufgabe").setExecutor(new TestaufgabeCommand());
        new ItemManager().start();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDestroyHoneyBlockListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPlaceHoneyBlockListener(), this);
    }
}
