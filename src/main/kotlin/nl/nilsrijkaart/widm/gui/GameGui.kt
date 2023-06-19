package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import nl.nilsrijkaart.widm.game.GameManager
import org.bukkit.Material
import org.bukkit.entity.Player

class GameGui {

    companion object {
        fun open(game: Game, player: Player) {
            val inventory = MoleGui("Game ${game.name}", 9)
            inventory.addItem(0, Material.SKELETON_SKULL, "&6Spelers", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GamePlayerGui.open(game, player)
            }

            inventory.addItem(1, Material.BOOK, "&6Regels", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameRuleGui.openGui(game, player)
            }

            inventory.addItem(2, Material.GREEN_WOOL, "&aPotje starten", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                game.start()
            }

            inventory.addItem(3, Material.RED_WOOL, "&cPotje stoppen", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameManager.game = null
            }


            inventory.addItem(8, Material.LEVER, "&cTerug", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameListGui.open(player)
            }

            inventory.open(player)
        }
    }
}