package nl.nilsrijkaart.widm

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.events.DeathEvent
import nl.nilsrijkaart.widm.events.EventJoin
import nl.nilsrijkaart.widm.events.PvpEvent
import nl.nilsrijkaart.widm.game.GameCommand
import nl.nilsrijkaart.widm.gui.GameColorCommand
import nl.nilsrijkaart.widm.util.ScoreboardUtil
import nl.nilsrijkaart.widm.util.gameUtil.*
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var plugin: JavaPlugin
        val deathRequest = mutableListOf<Player>()
        val reviveRequest = mutableListOf<Pair<Player, Player>>()
        val teleportRequests = mutableMapOf<Player, Player>()
    }

    override fun onEnable() {
        plugin = this
        Bukkit.getPluginManager().registerEvents(EventJoin(), this)
        Bukkit.getPluginManager().registerEvents(ChatEvent(), this)
        Bukkit.getPluginManager().registerEvents(PvpEvent(), this)
        Bukkit.getPluginManager().registerEvents(DeathEvent(), this)

        getCommand("game")?.setExecutor(GameCommand())
        getCommand("gameutil")?.setExecutor(GameUtilCommand())
        getCommand("kleuren")?.setExecutor(GameColorCommand())
        getCommand("msg")?.setExecutor(MsgCommand())

        // run every 5 seconds of the server
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Runnable {
            deathRequest.forEach {
                it.health = 0.0
                deathRequest.remove(it)
            }

            reviveRequest.forEach {
                it.first.health = 20.0
                it.first.gameMode = GameMode.SURVIVAL
                it.first.health = 20.0
                it.first.foodLevel = 20
                it.first.inventory.clear()
                it.first.teleport(it.second)
                reviveRequest.remove(it)
            }

            teleportRequests.forEach { (player, target) ->
                player.teleport(target)
                teleportRequests.remove(player)
            }

            Bukkit.getOnlinePlayers().forEach {
                ScoreboardUtil.updateScoreboard(it)
            }
        }, 0L, 10L)

        DeathNote()
        InventoryCheck()
        TeleportUtil()
        ReviveUtil()
        MsgDeathNote()
    }
}