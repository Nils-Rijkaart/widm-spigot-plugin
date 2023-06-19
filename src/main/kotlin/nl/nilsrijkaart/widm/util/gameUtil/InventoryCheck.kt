package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class InventoryCheck {

    companion object {

        private val allowedSyntax = listOf("inventorycheck", "inventory check", "invcheck", "inv check")

        init {
            ChatEvent.hookChat {
                if (allowedSyntax.contains(it.message.split(" ")[0].lowercase())) {
                    if (GameManager.game?.rules?.get(GameRule.AUTO_UTIL) != true) {
                        return@hookChat
                    }

                    if (it.message.split(" ").size < 2) {
                        it.player.sendMessage(formattedMessage("&cGebruik inventorycheck <speler>"))
                        return@hookChat
                    }

                    val target = Bukkit.getPlayer(it.message.split(" ")[1])
                    if (target == null) {
                        it.player.sendMessage(formattedMessage("&cDeze speler bestaat niet."))
                        return@hookChat
                    }

                    execute(it.player, target)

                }
            }
        }

        fun execute(sender: Player, target: Player) {


        }

        fun give(player: Player) {

        }

    }
}
