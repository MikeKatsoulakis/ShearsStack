package me.mikedev.ShearsStack.Listeners;

import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickInventoryEvent implements Listener {

    /*
     *
     * Inventory Click Event
     *
     */

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        Inventory inv = e.getInventory(); //The Clicked inventory
        Player p = (Player) e.getWhoClicked();//The Player who clicked

        /*
         *
         * Checking the click, and its location
         *
         */

        if (e.getClick().isLeftClick()) {
            if (e.getCursor().getType() == Material.SHEARS) {
                if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {

                    /*
                     *
                     * Cancelling the event if the click is outside the inventory
                     *
                     */

                    e.setCancelled(true);
                    p.getWorld().dropItem(p.getLocation(), e.getCursor()); // Dropping the clicked ItemStack
                    e.setCursor(null); // Removing the Dropped Item from the players cursor

                }

                else if (e.getSlotType() == InventoryType.SlotType.CONTAINER || e.getSlotType() == InventoryType.SlotType.QUICKBAR) {

                    ItemStack onCursor = e.getCursor(); //Getting the clicked ItemStack
                    ItemStack onSlot = inv.getItem(e.getSlot()); //Getting the ItemStack on the clicked Slot

                    /*
                     *
                     * NMS copies of the above ItemStacks
                     *
                     */

                    net.minecraft.server.v1_13_R2.ItemStack onCursorNMS = CraftItemStack.asNMSCopy(onCursor);
                    net.minecraft.server.v1_13_R2.ItemStack onSlotNMS = CraftItemStack.asNMSCopy(onSlot);

                    /*
                     *
                     * Getting the NBTTags of the above NMS ItemStacks
                     *
                     */

                    NBTTagCompound onCursorTag = onCursorNMS.getTag();
                    NBTTagCompound onSlotTag = onSlotNMS.getTag();

                    if(onCursorTag != null && onSlotTag != null) {
                        if (onCursorTag == onSlotTag) { // If the Tags are the same, We stack them

                            e.setCancelled(true); // Cancelling the event

                            /*
                             *
                             * Creating the new ItemStack
                             *
                             */

                            int newSize = onCursor.getAmount() + onSlot.getAmount(); // Calculating new size
                            ItemStack toGive = new ItemStack(Material.SHEARS, newSize); // Making the ItemStack

                            net.minecraft.server.v1_13_R2.ItemStack toGiveNMS = CraftItemStack.asNMSCopy(toGive); //Getting the NMS Copy of the above ItemStack, to pass the NBTTag
                            toGiveNMS.setTag(onCursorTag); //Setting the NBTTag

                            toGive = CraftItemStack.asBukkitCopy(toGiveNMS); //Getting the ItemStack back to it's original Bukkit form

                            e.setCursor(null); //Removing the current item the player has in their cursor
                            inv.setItem(e.getSlot(), toGive); //Setting the new ItemStack at the slot of the old one
                        }
                    }
                }
            }
        }
    }
}
