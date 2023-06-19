package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import org.bukkit.Material
import org.bukkit.entity.Player

class GameRuleGui {
    companion object {

        fun openGui(game: Game, player: Player) {
            val inventory = MoleGui("&6Regels - ${game.name}", 3 * 9)
            game.rules.onEachIndexed { index, entry ->
                inventory.addItem(
                    index,
                    entry.key.material,
                    "&6${entry.key.displayName}",
                    entry.key.description
                ) { clickEvent ->
                    clickEvent.isCancelled = true
                }
                inventory.addItem(
                    index + 9, if (entry.value) {
                        Material.GREEN_WOOL
                    } else {
                        Material.RED_WOOL
                    }, "&6${entry.key.displayName}", entry.key.description
                ) { clickEvent ->
                    clickEvent.isCancelled = true
                    game.rules[entry.key] = !game.rules[entry.key]!!
                    openGui(game, player)
                }
            }

            inventory.addItem(26, Material.LEVER, "&cTerug", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameGui.open(game, player)
            }

            inventory.open(player)
        }
    }
}