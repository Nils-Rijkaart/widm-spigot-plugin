package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import org.bukkit.Material
import org.bukkit.entity.Player

class GamePlayerGui {

    companion object {

        fun open(game: Game, player: Player) {
            val inventory = MoleGui("&6Spelers - ${game.name}", 27)

            game.slots.forEach { entry ->

                inventory.addItem(
                    -1,
                    entry.color.material,
                    "&${entry.color.code}${entry.color.name}",
                    listOf()
                ) { clickEvent ->
                    clickEvent.isCancelled = true
                    GamePlayerEditGui.open(player, game, entry)
                }

            }

            inventory.addItem(-1, Material.DIAMOND_BLOCK, "&aNieuwe rol toevoegen", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                game.addRole()
                game.save()
                open(game, player)
            }

            inventory.open(player)
        }
    }
}