package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import nl.nilsrijkaart.widm.game.GameSlot
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class SoulBoundGui {

    companion object {

        fun open(game: Game, player: Player, gameSlot: GameSlot) {
            val moleGui = MoleGui("&6Soulbound - ${gameSlot.color.name}", 27)

            game.slots.filter {
                it != gameSlot
            }.forEach { gSlot ->

                val playerName = if (gSlot.player == null) {
                    "Willekeurig"
                } else {
                    Bukkit.getOfflinePlayer(gSlot.player!!).name
                }
                if (gameSlot.soulBounds?.contains(gSlot.color) == true) {
                    moleGui.addItem(
                        -1,
                        gSlot.color.material,
                        "&a${gSlot.color.displayName}",
                        listOf(
                            "&6Speler: &7$playerName",
                            "&aLinker muisknop om soulbound",
                            "&atweezijdig te verwijderen",
                            "&6 ",
                            "&aRechter muis knop om soulbound",
                            "&aeenzijdig te verwijderen"
                        )
                    ) {
                        gameSlot.soulBounds = gameSlot.soulBounds?.filter { soulBound -> soulBound != gSlot.color }

                        if (it.isRightClick) {
                            open(game, player, gameSlot)
                            return@addItem
                        }

                        gSlot.soulBounds = gSlot.soulBounds?.filter { soulBound -> soulBound != gameSlot.color }

                        open(game, player, gameSlot)
                    }
                } else {
                    moleGui.addItem(
                        -1,
                        gSlot.color.material,
                        "&c${gSlot.color.displayName}",
                        listOf(
                            "&6Speler: &7$playerName",
                            "&cLinker muisknop om soulbound",
                            "&ctweezijdig toe te voegen",
                            "&6 ",
                            "&cRechter muis knop om soulbound",
                            "&ceenzijdig toe te voegen"
                        )
                    ) {

                        gameSlot.soulBounds = (gameSlot.soulBounds?: listOf()) + gSlot.color

                        if (it.isRightClick) {
                            open(game, player, gameSlot)
                            return@addItem
                        }

                        gSlot.soulBounds = (gSlot.soulBounds?: listOf()) + gameSlot.color

                        open(game, player, gameSlot)
                    }
                }
            }

            moleGui.addItem(26, Material.LEVER, "&cTerug", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GamePlayerEditGui.open(player, game, gameSlot)
            }

            moleGui.open(player)
        }
    }
}