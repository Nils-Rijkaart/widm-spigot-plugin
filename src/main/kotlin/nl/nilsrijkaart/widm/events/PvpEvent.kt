package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class PvpEvent : Listener {
    @EventHandler
    fun entityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        if (GameManager.game?.rules?.get(GameRule.PVP) == false) {
            if (event.damager is Player && event.entity is Player) {
                event.isCancelled = true
            }
        }
    }
}