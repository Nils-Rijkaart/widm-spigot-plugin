package nl.nilsrijkaart.widm

import nl.nilsrijkaart.widm.events.EventJoin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var plugin : JavaPlugin
    }
    override fun onEnable() {
        plugin = this
        Bukkit.getPluginManager().registerEvents(EventJoin(), this)
    }

}