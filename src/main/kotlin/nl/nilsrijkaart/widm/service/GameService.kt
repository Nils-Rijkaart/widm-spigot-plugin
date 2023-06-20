package nl.nilsrijkaart.widm.service

import nl.nilsrijkaart.widm.Main
import nl.nilsrijkaart.widm.game.Game
import nl.nilsrijkaart.widm.game.GameData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import kotlin.random.Random
import kotlin.random.nextUInt

class GameService {
    companion object {
        private val gameService = JsonService(Main.plugin, "games", GameData::class.java)
        private val games = mutableListOf<Game>()

        init {
            games.addAll(gameService.loadAll().map {
                val game = Game(it.id, it.name)
                game.hosts = it.hosts.toMutableList()
                game.rules = it.rules.toMutableMap()
                game.slots = it.slots.toMutableList()
                game
            })
        }

        fun saveGame(game: Game) {
            val gameData = GameData(game.id, game.name, game.hosts, game.slots, game.rules)
            gameService.save(gameData, game.id.toString())
        }

        fun getGames(player: Player): List<Game> = games.filter { it.hosts.contains(player.uniqueId) }

        fun createGame(player: Player) {
            val gameId = Random.nextInt(0,99999)
            val game = Game(gameId, "Game $gameId")
            game.hosts = listOf(player.uniqueId).toMutableList()
            games.add(game)
            saveGame(game)
        }

        fun deleteGame(game: Game) {
            games.remove(game)
            gameService.delete(game.id.toString())
        }
    }
}