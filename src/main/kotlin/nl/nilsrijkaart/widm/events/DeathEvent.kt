package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent

class DeathEvent : Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        if (GameManager.game?.rules?.get(GameRule.DEATH_MESSAGE) != true) {
            event.deathMessage = null
            return
        }
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        if (GameManager.game != null) {
            val host = GameManager.game!!.hosts.first {
                it.isOnline
            }
            event.player.teleport(host.location)
            event.player.gameMode = GameMode.SPECTATOR
            event.player.sendMessage("§cJe bent dood gegaan :( Je kunt nu alleen nog maar toekijken. Respecteer hierbij de regels, ga niet verder kamers in dan de game is en kijk niet in kisten.")
        }
    }
}