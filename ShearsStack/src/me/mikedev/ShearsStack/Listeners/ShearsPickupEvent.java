package me.mikedev.ShearsStack.Listeners;

import me.mikedev.ShearsStack.Runnables.UpdateRunnable;
import me.mikedev.ShearsStack.ShearsStackMain;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ShearsPickupEvent implements Listener {

    /*
     *
     * Main class instance
     *
     */

    private ShearsStackMain plugin = ShearsStackMain.getInstance();

    /*
     *
     * Shear Pick-up Event(PlayerPickupItemEvent)
     *
     */

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {

        if (e.getItem().getItemStack().getType() == Material.SHEARS) { // Checking if the type of the item the player picked up is Shears

            Player p = e.getPlayer(); // The player

            /*
             *
             * Runnable to run the code inside just 5 ticks(quarter of a second assuming the server is on ~20 TPS) after the player picked up the item.
             * (If we do it immediately, depending on the servers lag, we may come across a NULL Item as it may have not yet been registered)
             *
             */

            UpdateRunnable runnable = new UpdateRunnable(p);
            runnable.runTaskLater(plugin, 5);

        }
    }
}
