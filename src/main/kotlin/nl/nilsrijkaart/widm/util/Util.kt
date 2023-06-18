package nl.nilsrijkaart.widm.util

import org.bukkit.ChatColor

fun formattedMessage(message: String): String {
    return ChatColor.translateAlternateColorCodes('&', message)
}