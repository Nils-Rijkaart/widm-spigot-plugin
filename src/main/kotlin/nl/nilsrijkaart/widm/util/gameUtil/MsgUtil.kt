package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.util.BookUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BookMeta
import kotlin.random.Random

class MsgUtil {
    companion object {

        fun sendMsg(sender: Player, receiver: Player, message: String) {
            sender.sendMessage("§7[§cMSG§7] §f${sender.name} §7-> §f${receiver.name}: §f$message")
            receiver.sendMessage("§7[§cMSG§7] §f${sender.name} §7-> §f${receiver.name}: §f$message")
        }

        fun useMsgUtil(sender: Player, receiver: Player, message: String) {
            val item = sender.inventory.find {
                if (it?.itemMeta is BookMeta?) {
                    val bookMeta = it?.itemMeta as BookMeta?
                    bookMeta?.title == "MSG Util"
                } else {
                    false
                }
            }

            if (item == null) {
                sender.sendMessage("§cJe mag geen MSG sturen zonder een MSG util.")
                return
            }

            item.amount -= 1
            sendMsg(sender, receiver, message)
            GameManager.game?.hosts?.forEach {
                Bukkit.getPlayer(it)?.sendMessage("§7[§cMSG§7] §f${sender.name} §7-> §f${receiver.name}: §f$message §7(via MSG util)")
            }
        }

        fun give(player: Player) {
            player.inventory.addItem(
                BookUtil.createBook(
                    "MSG Util",
                    "Spelmaker",
                    listOf(
                        "Dit is een message util. Je mag hiermee een privé bericht naar iemand sturen. De gamemaker en mensen die NIET in het potje zitten kan je sowieso een bericht sturen.",
                        "emand die dood is een bericht sturen kan ook, maar dan gebruik je het boekje.",
                        "ID: ${
                            Random.nextInt(10000, 99999)
                        }"
                    )
                )
            )
        }
    }
}