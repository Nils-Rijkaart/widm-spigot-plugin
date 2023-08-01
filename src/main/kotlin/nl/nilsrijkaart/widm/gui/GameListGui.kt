package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.service.GameService
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class GameListGui {

    companion object {
        fun open(player: Player) {

            val games = GameService.getGames(player)
            val inventory = MoleGui("Games", 36)

            games.forEach {
                val lore = mutableListOf<String>()
                lore.add(formattedMessage("&7Hosts:"))
                it.hosts.forEach { host ->
                    lore.add(formattedMessage("&7- ${Bukkit.getOfflinePlayer(host).name}"))
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

            inventory.addItem(35, Material.GOLD_BLOCK, "&6Rollen beheren", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
            }

            inventory.open(player)
        }
    }

}