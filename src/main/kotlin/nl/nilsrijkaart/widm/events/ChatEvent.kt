package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatEvent : Listener {
    @EventHandler
    fun chatEvent(event: AsyncPlayerChatEvent) {
        event.message = formattedMessage("&7${event.player.name} &8» &f${event.message}")
    }
}