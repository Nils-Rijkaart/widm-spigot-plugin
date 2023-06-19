package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class DeathEvent : Listener {
    @EventHandler
    fun onDeath(event : PlayerDeathEvent) {
        if(GameManager.game?.rules?.get(GameRule.DEATH_MESSAGE) != true) {
            event.deathMessage = null
            return
        }
    }
}