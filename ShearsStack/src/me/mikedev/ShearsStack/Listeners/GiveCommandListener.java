package me.mikedev.ShearsStack.Listeners;

import me.mikedev.ShearsStack.Runnables.UpdateRunnable;
import me.mikedev.ShearsStack.ShearsStackMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;



public class GiveCommandListener implements Listener {

    private ShearsStackMain plugin = ShearsStackMain.getInstance();

    @EventHandler
    public void onGiveCommand(PlayerCommandPreprocessEvent e){

        String cmd = e.getMessage().replaceFirst("/", "");
        String[] args =  cmd.split(" ");

        if(args[0].equalsIgnoreCase("give")){

            if(Bukkit.getPlayer(args[1]).isOnline()){

                if(args[2].contains("shears")){

                    Player p = Bukkit.getPlayer(args[1]); // The player

                    /*
                     *
                     * Runnable to run the code inside just 5 ticks(quarter of a second assuming the server is on ~20 TPS) after the player got the item.
                     * (If we do it immediately, depending on the servers lag, we may come across a NULL Item as it may have not yet been registered)
                     *
                     */

                    UpdateRunnable runnable = new UpdateRunnable(p);
                    runnable.runTaskLater(plugin, 5);

                }
            }
        }
    }
}
