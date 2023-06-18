package nl.nilsrijkaart.widm.util

import org.bukkit.ChatColor

class Util {
}

fun formattedMessage(message: String): String {
    return ChatColor.translateAlternateColorCodes('&', message)
}