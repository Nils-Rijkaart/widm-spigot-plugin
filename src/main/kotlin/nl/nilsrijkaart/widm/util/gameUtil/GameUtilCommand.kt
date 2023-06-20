package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.game.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GameUtilCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "gameutil") return true
        if (sender is Player) {

            if (GameManager.game?.slots?.any {
                    it.player == sender.uniqueId
                } == true) {
                sender.sendMessage("§cJe kan dit command niet gebruiken als je in het spel zit.")
                return true
            }

            if (args.isEmpty()) {
                sender.sendMessage("§cUsage: /gameutil <util> (deathnote, msgdeathnote, inventorycheck, msg, revive, teleport)")
            } else if (args[0].lowercase() == "deathnote") {
                DeathNote.give(sender)
            } else if (args[0].lowercase() == "inventorycheck") {
                InventoryCheck.give(sender)
            } else if (args[0].lowercase() == "msg") {
                MsgUtil.give(sender)
            } else if (args[0].lowercase() == "revive") {
                ReviveUtil.give(sender)
            } else if (args[0].lowercase() == "teleport") {
                TeleportUtil.give(sender)
            } else if (args[0].lowercase() == "msgdeathnote") {
                MsgDeathNote.give(sender)
            } else {
                sender.sendMessage("§cUsage: /gameutil <util> (deathnote, msgdeathnote, inventorycheck, msg, revive, teleport)")
            }
        }
        return true
    }
}