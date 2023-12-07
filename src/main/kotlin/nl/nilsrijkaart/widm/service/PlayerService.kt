package nl.nilsrijkaart.widm.service

import nl.nilsrijkaart.widm.Main
import nl.nilsrijkaart.widm.entity.MolePlayer
import java.util.UUID

class PlayerService {
    companion object {


        val playerService = JsonService(Main.plugin, "players", MolePlayer::class.java)
        val players = mutableMapOf<UUID, MolePlayer>()


        init {
            load()
        }

        fun getPlayer(uuid: UUID): MolePlayer {
            return players.getOrPut(uuid) { MolePlayer(uuid, 0, mutableMapOf(), mutableMapOf()) }
        }

        fun updatePlayer(player: MolePlayer) {
            players[player.uuid] = player
            playerService.save(player, player.uuid.toString())
        }

        fun save() {
            players.forEach {
                playerService.save(it.value, it.key.toString())
            }
        }

        private fun load() {
            players.clear()
            playerService.loadAll().forEach {
                players[it.uuid] = it
            }
        }

    }

}