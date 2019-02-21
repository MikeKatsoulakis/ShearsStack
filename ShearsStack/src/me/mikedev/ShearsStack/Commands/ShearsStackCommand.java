package me.mikedev.ShearsStack.Commands;

import me.mikedev.ShearsStack.Runnables.UpdateRunnable;
import me.mikedev.ShearsStack.ShearsStackMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShearsStackCommand implements CommandExecutor {

    private ShearsStackMain plugin = ShearsStackMain.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        /*
         *
         * The sender should be a player
         *
         */

        if(commandSender instanceof Player){

            Player p = (Player) commandSender; // The player that executed the command

            /*
             *
             * The player needs to have a certain permission
             *
             */

            if(p.hasPermission("shearsstack.stack")) {

                /*
                 *
                 * Runnable to run the code inside just 5 ticks(quarter of a second assuming the server is on ~20 TPS)
                 * (If we do it immediately, depending on the servers lag, we may come across a NULL Item as it may have not yet been registered)
                 *
                 */

                UpdateRunnable runnable = new UpdateRunnable(p);
                runnable.runTaskLater(plugin, 5);

            }
        }
        return false;
    }
}
