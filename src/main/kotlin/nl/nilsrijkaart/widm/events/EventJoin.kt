package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.util.ScoreboardUtil
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class EventJoin : Listener {

    @EventHandler
    fun joinEvent(event: PlayerJoinEvent) {

        ScoreboardUtil.createScoreboard(event.player)

        event.joinMessage = if (GameManager.game != null) {
            GameManager.game?.hosts?.forEach {
                it.sendMessage(formattedMessage("&a[&2&l+&a] &2${event.player.name}"))
            }
            ""
        } else {
            formattedMessage("&a[&2&l+&a] &2${event.player.name}")
        }
    }

    @EventHandler
    fun quitEvent(event: PlayerQuitEvent) {
        event.quitMessage = if (GameManager.game != null) {
            GameManager.game?.hosts?.forEach {
                it.sendMessage(formattedMessage("&4[&c&l-&4] &c${event.player.name}"))
            }
            ""
        } else {
            formattedMessage("&4[&c&l-&4] &c${event.player.name}")
        }
    }

}
