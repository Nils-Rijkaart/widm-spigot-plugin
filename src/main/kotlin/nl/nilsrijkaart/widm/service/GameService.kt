package nl.nilsrijkaart.widm.service

import nl.nilsrijkaart.widm.game.Game
import org.bukkit.entity.Player

class GameService {
    companion object {
        private val gameService = JsonService("games", Game::class.java)
        private val games = mutableListOf<Game>()

        init {
            games.addAll(gameService.loadAll())
        }

        fun saveGame(game: Game) {
            gameService.save(game, game.id.toString())
        }

        fun loadGame(id: Int): Game? {
            return gameService.load(id.toString())
        }

        fun getGames(player: Player): List<Game> = games.filter { it.hosts.contains(player) }

        fun createGame(player: Player) {
            val game = Game(games.size, "Game ${games.size}")
            game.hosts = listOf(player)
            games.add(game)
            saveGame(game)
        }
    }
}