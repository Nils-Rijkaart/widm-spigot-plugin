package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.util.BookUtil
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class DeathNote {
    companion object {
        init {
            ChatEvent.hookChat {
                val player = it.player
                val message = it.message
                if (message.lowercase().startsWith("deathnote") || message.lowercase().startsWith("death note")) {
                    if (it.message.split(" ").size < 2) {
                        player.sendMessage(formattedMessage("&cGebruik deathnote <speler>"))
                        return@hookChat
                    }

                    val target = Bukkit.getPlayer(message.split(" ")[1])
                    if (target == null) {
                        player.sendMessage(formattedMessage("&cDeze speler bestaat niet."))
                        return@hookChat
                    }
                    //TODO: check if player is alive & in game

                    execute(player, target)
                }
            }
        }

        private fun execute(sender: Player, target: Player) {
            val item = sender.inventory.first {
                it?.itemMeta?.displayName == "Deathnote"
            }
            if (item == null) {
                sender.sendMessage(formattedMessage("&cJe hebt geen deathnote in je inventory."))
                return
            }

            target.health = 0.0
            item.amount -= 1
        }


        fun give(player: Player) {
            player.inventory.addItem(
                BookUtil.createBook(
                    "Deathnote",
                    "Spelmaker",
                    listOf("Dit is een deathnote. Gebruik deze deathnote om een speler direct dood te maken. Je gebruikt dit boekje door in chat te typen: deathnote <spelernaam>")
                )
            )
        }
    }
}