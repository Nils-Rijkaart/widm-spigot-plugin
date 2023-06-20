package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.Main
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

class TeleportUtil {
    companion object {
        init {
            ChatEvent.hookChat {
                val player = it.player
                val message = it.message
                if(message.split(" ").size < 4) return@hookChat
                if ((message.lowercase().startsWith("teleport") || message.lowercase().startsWith("tp")) && message.split(" ")[2].lowercase() == "naar") {
                    if (GameManager.game?.rules?.get(GameRule.AUTO_UTIL) != true) {
                        return@hookChat
                    }

                    val target = Bukkit.getPlayer(message.split(" ")[1])
                    val secondTarget = Bukkit.getPlayer(message.split(" ")[3])
                    if (target == null || secondTarget == null) {
                        player.sendMessage(formattedMessage("&cDeze speler bestaat niet."))
                        return@hookChat
                    }
                    execute(player, target, secondTarget)
                }
            }
        }

        private fun execute(sender: Player, target: Player, secondTarget: Player) {

            val item = sender.inventory.find {
                if (it?.itemMeta is BookMeta?) {
                    val bookMeta = it?.itemMeta as BookMeta?
                    bookMeta?.title == "Teleport"
                } else {
                    false
                }
            }
            if (item == null) {
                sender.sendMessage(formattedMessage("&cJe hebt geen teleport in je inventory."))
                return
            }

            if (GameManager.game?.getRole(target) == null || GameManager.game?.getRole(secondTarget) == null) {
                sender.sendMessage(formattedMessage("&cDie speler zit niet in het potje."))
                return
            }

            if (target.gameMode == GameMode.SPECTATOR || secondTarget.gameMode == GameMode.SPECTATOR) {
                sender.sendMessage(formattedMessage("&cDeze speler is= dood."))
                return
            }

            Main.teleportRequests[target] = secondTarget
            item.amount -= 1
        }

        fun give(player: Player) {
            player.inventory.addItem(
                BookUtil.createBook(
                    "Teleport",
                    "Spelmaker",
                    listOf(
                        "Dit is een teleport. Teleporteer hier direct naar een andere speler. Gebruik door: teleport <speler> naar <speler>",
                        "ID: ${
                            Random.nextInt(10000, 99999)
                        }"
                    )
                )
            )
        }
    }
}