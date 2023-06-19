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
                val predefined = game.slots.any {
                    it.player == gamePlayer.uniqueId
                }

                inventory.addSkull(-1, gamePlayer.name, "&c${gamePlayer.name}", listOf(), predefined) { clickEvent ->
                    clickEvent.isCancelled = true
                    if (clickEvent.isRightClick) {
                        if(predefined) {
                            game.slots.first { it.player == gamePlayer.uniqueId }.player = null

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