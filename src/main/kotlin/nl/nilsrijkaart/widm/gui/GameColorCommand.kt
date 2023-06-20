package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GameColorCommand : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name != "kleuren") return true
        if(sender !is Player) return true

        if(GameManager.game == null) {
            sender.sendMessage("§cEr is geen spel bezig!")
            return true
        }

        if(GameManager.game!!.rules[GameRule.COLORS_COMMAND] == false) {
            sender.sendMessage("§cDeze functie is uitgeschakeld!")
            return true
        }

        val slots = GameManager.game!!.slots

        sender.sendMessage("§6Kleuren:")
        slots.forEach {
            val roleNotion = if(GameManager.game!!.hosts.contains(sender.uniqueId)) { "(§7${it.role.displayName}§6)" } else { "" }
            sender.sendMessage("§${it.color.code}${it.color.displayName}§f: ${it.player?.let {p -> Bukkit.getPlayer(p)?.name } ?: "§cGeen speler"} §6$roleNotion")
        }

        return true
    }

}