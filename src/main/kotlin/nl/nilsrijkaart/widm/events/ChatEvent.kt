package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.UUID

class ChatEvent : Listener {
    @EventHandler
    fun chatEvent(event: AsyncPlayerChatEvent) {
        if(hooks[event.player.uniqueId] != null) {
            event.isCancelled = true
            hooks[event.player.uniqueId]?.invoke(event)
            hooks.remove(event.player.uniqueId)
            return
        }

        event.message = formattedMessage("&7${event.player.name} &8» &f${event.message}")


    }

    companion object {
        val hooks = mutableMapOf<UUID, (AsyncPlayerChatEvent) -> Unit>()
        fun hookChat(uuid : UUID, cb : (event : AsyncPlayerChatEvent) -> Unit){
            hooks[uuid] = cb
        }
    }
}