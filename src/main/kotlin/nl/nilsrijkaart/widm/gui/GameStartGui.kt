package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class GameStartGui {
    companion object {
        fun open(player: Player, game: Game) {
            val inventory = MoleGui("Game ${game.name} starten", 27)
            Bukkit.getOnlinePlayers().forEach { gamePlayer ->
                val predefined = game.slots.any {
                    it.player == gamePlayer.uniqueId
                }

                val randomSlots = game.slots.filter { it.player == null }
                var filledSlots = 0
                inventory.addSkull(-1, gamePlayer.name, "&6${gamePlayer.name}", listOf(), predefined) { clickEvent ->
                    clickEvent.isCancelled = true
                    if (!predefined) {

                    }
                    open(player, game)
                }
            }
        }
    }
}