package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import nl.nilsrijkaart.widm.util.BookUtil
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BookMeta
import kotlin.random.Random

class ReviveUtil {

    companion object {
        init {
            ChatEvent.hookChat {
                val player = it.player
                val message = it.message
                if (message.lowercase().startsWith("revive")) {
                    if (GameManager.game?.rules?.get(GameRule.AUTO_UTIL) != true) {
                        return@hookChat
                    }

                    if (it.message.split(" ").size < 2) {
                        player.sendMessage(formattedMessage("&cGebruik revive <speler>"))
                        return@hookChat
                    }

                    val target = Bukkit.getPlayer(message.split(" ")[1])
                    if (target == null) {
                        player.sendMessage(formattedMessage("&cDeze speler bestaat niet."))
                        return@hookChat
                    }
                    execute(player, target)
                }
            }
        }

        private fun execute(sender: Player, target: Player) {

            val item = sender.inventory.find {
                if (it?.itemMeta is BookMeta?) {
                    val bookMeta = it?.itemMeta as BookMeta?
                    bookMeta?.title == "Revive"
                } else {
                    false
                }
            }
            if (item == null) {
                sender.sendMessage(formattedMessage("&cJe hebt geen revive in je inventory."))
                return
            }

            if (GameManager.game?.getRole(target) == null) {
                sender.sendMessage(formattedMessage("&cDie speler zit niet in het potje."))
                return
            }

            if (target.gameMode != GameMode.SPECTATOR && target.gameMode != GameMode.CREATIVE) {
                sender.sendMessage(formattedMessage("&cDeze speler is niet dood."))
                return
            }

            target.gameMode = GameMode.SURVIVAL
            target.health = 20.0
            target.foodLevel = 20
            target.sendMessage(formattedMessage("&aJe bent weer tot leven gewekt door ${sender.displayName}"))
            sender.sendMessage(formattedMessage("&aJe hebt ${target.displayName} weer tot leven gewekt."))
            target.teleport(sender.location)
        }

        fun give(player: Player) {
            player.inventory.addItem(
                BookUtil.createBook(
                    "Revive",
                    "Spelmaker",
                    listOf(
                        "Dit is een Revive. Breng hiermee iemand terug uit de dood. Gebruik revive <spelernaam>",
                        "ID: ${
                            Random.nextInt(10000, 99999)
                        }"
                    )
                )
            )
        }


    }

}