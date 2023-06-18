package nl.nilsrijkaart.widm.game

import org.bukkit.entity.Player

class Game(val id: Int, var name: String) {

    var hosts: List<Player> = listOf()

    //default settings, otherwise loaded from json
    var rules = mutableMapOf(
        GameRule.PVP to true,
        GameRule.MSG to false,
        GameRule.PK_DEATHNOTE to false
    )

    fun save() {
    }


}