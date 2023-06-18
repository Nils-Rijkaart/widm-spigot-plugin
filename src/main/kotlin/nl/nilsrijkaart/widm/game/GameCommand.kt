package nl.nilsrijkaart.widm.game

import nl.nilsrijkaart.widm.gui.GameListGui
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GameCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (!sender.hasPermission("widm.host")) return true
            GameListGui.open(sender)
        }
        return true
    }
}