package nl.nilsrijkaart.widm.game

import org.bukkit.Material

enum class GameColor(val displayName : String, val code : Char, val material : Material) {
    RED("Rood", 'c', Material.RED_WOOL),
    BLUE("Blauw", '9', Material.BLUE_WOOL),
    GREEN("Groen", 'a', Material.GREEN_WOOL),
    YELLOW("Geel", 'e', Material.YELLOW_WOOL),
    ORANGE("Oranje", '6', Material.ORANGE_WOOL),
    PURPLE("Paars", '5', Material.PURPLE_WOOL),
    PINK("Roze", 'd', Material.PINK_WOOL),
    BLACK("Zwart", '0', Material.BLACK_WOOL),
    WHITE("Wit", 'f', Material.WHITE_WOOL),
    GRAY("Grijs", '7', Material.GRAY_WOOL)
}

enum class GameRole(val item : Material) {
    MOLE(Material.DIAMOND_BLOCK), PLAYER(Material.GOLD_BLOCK), EGO(Material.SKELETON_SKULL)
}