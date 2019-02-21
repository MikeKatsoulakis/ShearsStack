package me.mikedev.ShearsStack.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ShearsThrowEvent implements Listener {

    /*
     *
     * Shear Throw Listener (PlayerDropItemEvent)
     *
     */

    @EventHandler
    public void onShearsThrough(PlayerDropItemEvent e){

        if(e.getItemDrop().getItemStack().getType() == Material.SHEARS){

            /*
             *
             * The player that dropped the shears
             *
             */

            Player p = e.getPlayer();

            /*
             *
             * The ItemStack that got dropped
             *
             */

            ItemStack itemStack = e.getItemDrop().getItemStack();

            if(itemStack.getAmount() != 1){

                /*
                 *
                 * Event cancelling
                 *
                 */

                e.setCancelled(true);

                /*
                 *
                 * Changing the ItemStack
                 *
                 */

                ItemStack itemToThrow = p.getItemInHand(); //Creating the new ItemStack (That will be thrown)
                itemToThrow.setAmount(1); //Setting the amount (always 1)
                p.getWorld().dropItem(p.getLocation(), itemToThrow); //Dropping the Item

                /*
                 *
                 * Removing the original stack from the players inventory
                 *
                 */

                p.getInventory().remove(itemStack);

                itemStack.setAmount(itemStack.getAmount() - 1); //Changing the amount of the original ItemStack
                itemStack.setDurability(Material.SHEARS.getMaxDurability()); //Maxing up the durability

                /*
                 *
                 * Adding the updated ItemStack(Different amount and durability) back to the player's inventory
                 *
                 */

                p.getInventory().addItem(itemStack);
            }
        }
    }
}
