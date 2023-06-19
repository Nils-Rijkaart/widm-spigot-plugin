package nl.nilsrijkaart.widm.game

import org.bukkit.Material

enum class GameRule(val displayName : String, val description : List<String>, val material : Material) {

    PVP("&4PVP", listOf("&cZet PVP aan of uit"), Material.IRON_SWORD),
    PK_DEATHNOTE("&4PK Deathnote", listOf("&cAan betekent dat", "&cde peacekeeper", "&cgedeathnote kan worden"), Material.BOOK),
    MSG("&4MSG", listOf("&cAan betekent dat", "&cspelers elkaar onderling", "&ckunnen msg'en"), Material.PAPER),
    AUTO_UTIL("&4Auto util", listOf("&cAan betekent dat", "&cutilities zoals een", "&cdeathnote automatisch", "&cuitgevoerd worden"), Material.REDSTONE),
    DEATH_MESSAGE("&4Death message", listOf("&cAan betekent dat", "&cspelers een death message", "&ckrijgen als ze doodgaan"), Material.SKELETON_SKULL),
}