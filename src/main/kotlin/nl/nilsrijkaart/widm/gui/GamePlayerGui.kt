package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import org.bukkit.entity.Player

class GamePlayerGui {

    companion object {

        fun open(game: Game, player: Player) {
            val inventory = MoleGui("&6Spelers - ${game.name}", (game.colors.size % 9 + 1) * 18)

            game.colors.onEachIndexed { index, entry ->
                // two rows: first row is the color, second row is the role. SO when index > 8, we need to add 9 to the index
                val calculatedIndex = (index % 9) * 9 + index
                inventory.addItem(
                    calculatedIndex,
                    entry.key.material,
                    "&${entry.key.code}${entry.key.name}",
                    listOf()
                ) { clickEvent ->
                    clickEvent.isCancelled = true
                    game.updateColor(entry.key)
                    game.save()
                    open(game, player)
                }

                inventory.addItem(
                    calculatedIndex + 9,
                    entry.value.item,
                    "&3${entry.value.name}",
                    listOf()
                ) { clickEvent ->
                    clickEvent.isCancelled = true
                    game.updateRole(entry.key, entry.value)
                    game.save()
                    open(game, player)
                }


            }

            inventory.open(player)
        }
    }
}