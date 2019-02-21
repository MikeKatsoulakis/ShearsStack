package me.mikedev.ShearsStack;

import me.mikedev.ShearsStack.Commands.ShearsStackCommand;
import me.mikedev.ShearsStack.Listeners.ClickInventoryEvent;
import me.mikedev.ShearsStack.Listeners.GiveCommandListener;
import me.mikedev.ShearsStack.Listeners.ShearsPickupEvent;
import me.mikedev.ShearsStack.Listeners.ShearsThrowEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ShearsStackMain extends JavaPlugin implements Listener {

    /*
     *
     * MAIN CLASS INSTANCE
     *
     */

    private static ShearsStackMain plugin;
    public static ShearsStackMain getInstance() { return plugin; }

    /*
     *
     * ON-ENABLE
     *
     */

    @Override
    public void onEnable() {

        getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[ShearsStack]" + ChatColor.GREEN + "Enabling.."); //Console Message

        plugin = this;

        /*
         *
         * EVENT REGISTERING
         *
         */

        getServer().getPluginManager().registerEvents(new ShearsThrowEvent(), this);
        getServer().getPluginManager().registerEvents(new ShearsPickupEvent(), this);
        getServer().getPluginManager().registerEvents(new GiveCommandListener(), this);
        getServer().getPluginManager().registerEvents(new ClickInventoryEvent(), this);

        /*
         *
         * COMMAND REGISTERING
         *
         */

        this.getCommand("stack").setExecutor(new ShearsStackCommand());

        getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[ShearsStack]" + ChatColor.GREEN + "Enabled"); //Console Message

    }
}
