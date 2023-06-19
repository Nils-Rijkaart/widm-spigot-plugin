package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class GameStartGui {
    companion object {
        fun open(player: Player, game: Game) {
            val inventory = MoleGui("Game ${game.name} starten", 27)
            Bukkit.getOnlinePlayers().forEach { gamePlayer ->
                val predefined = if (game.slots.any {
                        it.player == gamePlayer.uniqueId
                    }) {
                    val color = game.slots.first { it.player == gamePlayer.uniqueId }.color
                    listOf(
                        "&aDeze speler is",
                        "&agekoppeld aan de",
                        "&akleur: &${color.code}${color.displayName}",
                        "&aklik rechter muisknop",
                        "&aom te ontkoppelen"
                    )
                } else {
                    listOf("&cDeze speler zit", "&cop dit moment", "&cniet in het potje.")
                }

                inventory.addSkull(-1, gamePlayer.name, "&c${gamePlayer.name}", predefined, false) { clickEvent ->
                    clickEvent.isCancelled = true
                    if (clickEvent.isRightClick) {
                        val slot = game.slots.firstOrNull { it.player == gamePlayer.uniqueId }
                        if (slot != null) {
                            slot.player = null
                        }
                    } else {
                        game.assignRandomRole(gamePlayer)
                    }
                    open(player, game)
                }
            }

            inventory.addItem(25, Material.COMMAND_BLOCK, "&aStarten", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                game.start()
            }

            inventory.addItem(26, Material.LEVER, "&cTerug", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameGui.open(game, player)
            }



            inventory.open(player)
        }
    }
}