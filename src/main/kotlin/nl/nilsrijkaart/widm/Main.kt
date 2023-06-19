package nl.nilsrijkaart.widm

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.events.EventJoin
import nl.nilsrijkaart.widm.events.PvpEvent
import nl.nilsrijkaart.widm.game.GameCommand
import nl.nilsrijkaart.widm.util.ScoreboardUtil
import nl.nilsrijkaart.widm.util.gameUtil.DeathNote
import nl.nilsrijkaart.widm.util.gameUtil.GameUtilCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable() {
        plugin = this
        Bukkit.getPluginManager().registerEvents(EventJoin(), this)
        Bukkit.getPluginManager().registerEvents(ChatEvent(), this)
        Bukkit.getPluginManager().registerEvents(PvpEvent(), this)
        getCommand("game")?.setExecutor(GameCommand())
        getCommand("gameutil")?.setExecutor(GameUtilCommand())

        // run every 5 seconds of the server
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Runnable {
            Bukkit.getOnlinePlayers().forEach {
                ScoreboardUtil.updateScoreboard(it)
            }
        }, 0L, 6000L)

        DeathNote()
    }
}