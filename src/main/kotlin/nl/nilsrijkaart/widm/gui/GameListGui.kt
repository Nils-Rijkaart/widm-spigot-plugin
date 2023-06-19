package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.service.GameService
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Material
import org.bukkit.entity.Player

class GameListGui {

    companion object {
        fun open(player: Player) {

            val games = GameService.getGames(player)
            val inventory = MoleGui("Games", 27)

            games.forEach {
                val lore = mutableListOf<String>()
                lore.add(formattedMessage("&7Hosts:"))
                it.hosts.forEach { host ->
                    lore.add(formattedMessage("&7- ${host.name}"))
                }
                inventory.addItem(-1, Material.BOOK, "&6${it.name}", lore) { clickEvent ->
                    clickEvent.isCancelled = true
                    GameGui.open(it, player)
                }
            }

            inventory.addItem(-1, Material.WRITABLE_BOOK, "&6Potje maken", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameService.createGame(player)
                open(player)
            }

            inventory.open(player)
        }
    }

}