package me.mikedev.ShearsStack.Runnables;

import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateRunnable extends BukkitRunnable {

    private Player p;

    public UpdateRunnable(Player player) {
        this.p = player;
    }

    @Override
    public void run() {

        List<ItemStack> toRemove = new ArrayList<>(); // The ItemStacks we will have to remove from the players inventory after we do our calculations
        HashMap<NBTTagCompound, Integer> toAdd = new HashMap<>();// The NBTTags of the above ItemStacks

        /*
         *
         * Looping through the player's inventory looking for Shears ItemStacks
         *
         */

        for (ItemStack itemStack : this.p.getInventory()) {
            if (itemStack != null && itemStack.getType() == Material.SHEARS) {

                /*
                 *
                 * Getting their NBTTags
                 *
                 */

                net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack); // The NMS copy of the Shears ItemStack
                NBTTagCompound tag = nmsStack.getTag(); // The NBTTags of the above NMS ItemStack

                /*
                 *
                 * If the map already contains that tag, we add the ItemStack's amount, to the map's value for that tag,
                 * otherwise, we add the new NBTTag to the map, with the ItemStack's amount as the value
                 *
                 */

                if (toAdd.containsKey(tag)) {
                    int amount = toAdd.get(tag);
                    toAdd.put(tag, amount + itemStack.getAmount());
                } else {
                    toAdd.put(tag, itemStack.getAmount());
                }
                toRemove.add(itemStack);// Adding the ItemStack to the list with the ones that should be removed
            }
        }

        /*
         *
         * Looping through and removing the ItemStacks, after checking if the inventory still contains the said ItemStack
         *
         */

        for (ItemStack itemStack : toRemove) {
            if (this.p.getInventory().contains(itemStack)) {
                this.p.getInventory().remove(itemStack);
            }
        }

        /*
         *
         * Looping through all the NBTTags from the map, Creating the ItemStacks, and adding them to the player's inventory
         *
         */

        for (HashMap.Entry entry : toAdd.entrySet()) {

            NBTTagCompound tag = (NBTTagCompound) entry.getKey(); // Getting the key(NBTTag) from the map
            int amount = (int) entry.getValue(); // Getting the value(amount (Integer)) from the map

            ItemStack itemStack = new ItemStack(Material.SHEARS); // Making the new ItemStack
            net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack); // Getting the NMS copy of the above ITemStack
            nmsStack.setTag(tag); // Setting the NBTTag
            itemStack = CraftItemStack.asBukkitCopy(nmsStack); // ReCreating the Bukkit ItemStack

            /*
             *
             * Constructing the ItemStacks to add to the players inventory.
             * Is the amount of the current tag is over 64, we need more than 1 ItemStack
             *
             */

            while (amount > 64) {

                itemStack.setAmount(64); // Making the amount of the ItemStack 64
                this.p.getInventory().addItem(itemStack); // Adding the ItemStack to the player's inventory
                amount -= 64; // Reducing the amount by 64 (a whole stack)
            }

            itemStack.setAmount(amount); // Making the last ItemStack with the remaining amount
            this.p.getInventory().addItem(itemStack); // Adding the above stack to the players inventory

        }

    }
}
