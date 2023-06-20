package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import nl.nilsrijkaart.widm.util.BookUtil
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import kotlin.random.Random

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

        private fun execute(sender: Player, target: Player) {
            val item = sender.inventory.find {
                if (it?.itemMeta is BookMeta?) {
                    val bookMeta = it?.itemMeta as BookMeta?
                    bookMeta?.title == "Inventory Check"
                } else {
                    false
                }
            }
            if (item == null) {
                sender.sendMessage(formattedMessage("&cJe hebt geen inventory check in je inventory."))
                return
            }

            Bukkit.broadcastMessage(formattedMessage("&7${sender.name} &6heeft een inventorycheck gedaan op &7${target.name}&6:"))
            item.amount -= 1

            target.inventory.forEach {
                if (it != null) {
                    Bukkit.broadcastMessage(formattedMessage("&6- ${itemStackToMessage(it)}"))
                }
            }

        }

        private fun itemStackToMessage(itemStack: ItemStack): String {
            var string = ""
            string += if (itemStack.type == Material.WRITTEN_BOOK) {
                val bookMeta = itemStack.itemMeta as BookMeta
                "&7${itemStack.amount}x ${bookMeta.title}"
            } else {
                val itemMeta = itemStack.itemMeta
                if(itemMeta?.hasDisplayName() == true) {
                    "&7${itemStack.amount}x ${itemMeta.displayName} (${itemStack.type.name})"
                } else {
                    "&7${itemStack.amount}x ${itemStack.type.name}"
                }
            }
            return string
        }

        fun give(player: Player) {
            player.inventory.addItem(
                BookUtil.createBook(
                    "Inventory Check",
                    "Spelmaker",
                    listOf(
                        "Dit is een Inventory Check. Gebruik deze inventory check om de inventory van iemand in chat te krijgen. Je gebruikt dit boekje door in chat te typen: inventorycheck <spelernaam>.",
                        "ID: ${
                            Random.nextInt(10000, 99999)
                        }"
                    )
                )
            )
        }
    }
}
