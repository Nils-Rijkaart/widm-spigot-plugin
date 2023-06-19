package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class DeathEvent : Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        if (GameManager.game != null) {
            val host = GameManager.game!!.hosts.first {
                it.isOnline
            }
            event.entity.teleport(host.location)
            event.entity.gameMode = GameMode.SPECTATOR
            event.entity.sendMessage("§cJe bent dood gegaan :( Je kunt nu alleen nog maar toekijken. Respecteer hierbij de regels, ga niet verder kamers in dan de game is en kijk niet in kisten.")
        }

        if (GameManager.game?.rules?.get(GameRule.DEATH_MESSAGE) != true) {
            event.deathMessage = null
            return
        }
    }
}