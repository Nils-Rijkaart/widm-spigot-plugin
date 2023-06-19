package nl.nilsrijkaart.widm.util.gameUtil

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GameUtilCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "gameutil") return true
        if (sender is Player) {
            if (args.isEmpty()) {
                sender.sendMessage("§cUsage: /gameutil <util> (deathnote, inventorycheck, itemcheck)")
            } else if(args[0].lowercase() == "deathnote") {
                DeathNote.give(sender)
            }
        }
        return true
    }
}