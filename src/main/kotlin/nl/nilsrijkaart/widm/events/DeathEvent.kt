package nl.nilsrijkaart.widm.events

import nl.nilsrijkaart.widm.Main
import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.game.GameRule
import nl.nilsrijkaart.widm.util.locationDataToSpigotLocation
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.scheduler.BukkitRunnable


class DeathEvent : Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        if (GameManager.game?.rules?.get(GameRule.DEATH_MESSAGE) != true) {
            event.deathMessage = null
            return
        }

        val deathGameSlot = GameManager.game!!.slots.find {
            it.player == event.entity.uniqueId
        }

        GameManager.game!!.slots.filter {
            it.player != event.entity.uniqueId && it.soulBounds?.contains(deathGameSlot?.color) == true
        }.forEach {

            if (it.player != null && Bukkit.getPlayer(it.player!!)?.gameMode != GameMode.SPECTATOR && Bukkit.getPlayer(it.player!!)?.health != 0.0) {
                Bukkit.getPlayer(it.player!!)?.health = 0.0
                GameManager.game!!.hosts.forEach { uuid ->
                    Bukkit.getPlayer(uuid)?.sendMessage("§c${Bukkit.getPlayer(it.player!!)?.name} is dood gegaan door een soulbound op ${event.entity.name}")
                }
            }
        }
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        if (GameManager.game != null) {
            if (GameManager.game!!.slots.any {
                    it.player == event.player.uniqueId
                }) {

                event.player.sendMessage("§cJe bent dood gegaan :( Je kunt nu alleen nog maar toekijken. Respecteer hierbij de regels, ga niet verder kamers in dan de game is en kijk niet in kisten.")

                // get original spawn location
                val spawnLocation = GameManager.game!!.slots.first {
                    it.player == event.player.uniqueId
                }.location?.let { locationDataToSpigotLocation(it) }

                // Timers since spigot does not handle this well. First teleport, essentials will put you back in survival so after further delay spectator mode.

                object : BukkitRunnable() {
                    override fun run() {
                        spawnLocation?.let { event.player.teleport(it) }


                    }
                }.runTaskLater(Main.plugin, 2)

                object : BukkitRunnable() {
                    override fun run() {
                        event.player.gameMode = GameMode.SPECTATOR

                    }
                }.runTaskLater(Main.plugin, 5)
                return
            }
        }
    }
}