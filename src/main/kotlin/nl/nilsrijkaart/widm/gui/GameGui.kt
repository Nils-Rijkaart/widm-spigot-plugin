package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.service.GameService
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class GameGui {

    companion object {
        fun open(player: Player) {

            val games = GameService.getGames(player)
            val inventory = MoleGui("Games", (games.size % 9 + 1) * 9)

            games.forEach {
                val lore = mutableListOf<String>()
                lore.add(formattedMessage("&7Hosts:"))
                it.hosts.forEach { host ->
                    lore.add(formattedMessage("&7- ${host.name}"))
                }
                inventory.addItem(Material.BOOK, "&6${it.name}", lore) { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            inventory.addItem(Material.WRITABLE_BOOK, "&6Potje maken", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameService.createGame(player)
            }

            inventory.open(player)
        }
    }

}