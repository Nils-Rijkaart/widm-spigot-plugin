package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.Main
import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRole
import nl.nilsrijkaart.widm.game.GameRule
import nl.nilsrijkaart.widm.util.BookUtil
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BookMeta
import kotlin.random.Random

class MsgDeathNote {
    companion object {
        init {
            ChatEvent.hookChat {
                val player = it.player
                val message = it.message
                if (message.lowercase().startsWith("msg deathnote") || message.lowercase().startsWith("msg death note")) {
                    it.isCancelled = true
                    if (GameManager.game?.rules?.get(GameRule.AUTO_UTIL) != true) {
                        return@hookChat
                    }

                    if (it.message.split(" ").size < 2) {
                        player.sendMessage(formattedMessage("&cGebruik msg deathnote <speler>"))
                        return@hookChat
                    }

                    val target = Bukkit.getPlayer(message.split(" ")[2])
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
            val item = sender.inventory.find {
                if (it?.itemMeta is BookMeta?) {
                    val bookMeta = it?.itemMeta as BookMeta?
                    bookMeta?.title == "MSG Deathnote"
                } else {
                    false
                }
            }
            if (item == null) {
                sender.sendMessage(formattedMessage("&cJe hebt geen msg deathnote in je inventory."))
                return
            }

            if (GameManager.game?.isPeacekeeper(target) == true && GameManager.game?.rules?.get(GameRule.PK_DEATHNOTE) == false) {
                sender.sendMessage(formattedMessage("&cJe mag geen peacekeeper deathnoten."))
                return
            }


            item.amount -= 1
            Main.deathRequest.add(target)
            GameManager.game?.hosts?.forEach {
                Bukkit.getPlayer(it)?.sendMessage(formattedMessage("&c${sender.name} heeft een msg deathnote gebruikt op ${target.name}"))
            }
        }


        fun give(player: Player) {
            player.inventory.addItem(
                BookUtil.createBook(
                    "MSG Deathnote",
                    "Spelmaker",
                    listOf(
                        "Dit is een deathnote. Gebruik deze deathnote om een speler direct dood te maken. Je gebruikt dit boekje door in chat te typen: msg deathnote <spelernaam>. Let op! Geen slash voor het command.",
                        "ID: ${
                            Random.nextInt(10000, 99999)
                        }"
                    )
                )
            )
        }
    }
}