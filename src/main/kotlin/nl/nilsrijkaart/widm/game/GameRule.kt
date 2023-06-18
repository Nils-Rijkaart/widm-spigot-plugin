package nl.nilsrijkaart.widm.game

import org.bukkit.Material

enum class GameRule(val displayName : String, val description : String, val material : Material) {

    PVP("&4PVP", "&cZet PVP aan of uit", Material.IRON_SWORD),
    PK_DEATHNOTE("&4PK Deathnote", "&cAan betekent dat de PK gedeathnote kan worden.", Material.BOOK),
    MSG("&4MSG", "&cAan betekent dat spelers onderling berichten kunnen sturen.", Material.PAPER),
}