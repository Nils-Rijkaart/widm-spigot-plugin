package nl.nilsrijkaart.widm.util

import nl.nilsrijkaart.widm.game.LocationData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location

fun formattedMessage(message: String): String {
    return ChatColor.translateAlternateColorCodes('&', message)
}

fun locationDataToSpigotLocation(locationData: LocationData): Location {
    return Location(
        Bukkit.getWorld(locationData.world),
        locationData.x,
        locationData.y,
        locationData.z,
        locationData.yaw,
        locationData.pitch
    )
}