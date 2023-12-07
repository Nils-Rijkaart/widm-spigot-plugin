package nl.nilsrijkaart.widm.service

import nl.nilsrijkaart.widm.gui.StatisticGui
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StatisticCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "statistieken") return true
        if (sender is Player)
            StatisticGui.open(sender)
        return true
    }
}