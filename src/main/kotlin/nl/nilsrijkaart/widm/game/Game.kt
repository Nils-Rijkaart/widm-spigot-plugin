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

    var colors = mutableMapOf<GameColor, GameRole>()

    fun addRole() {
        val availableColor = GameColor.values().firstOrNull { !colors.containsKey(it) }
        colors[availableColor!!] = GameRole.PLAYER
    }

    fun updateRole(color: GameColor, role: GameRole) {
        colors[color] = role
    }

    fun updateColor(former: GameColor) {
        val nextColor = GameColor.values().filter { !colors.containsKey(it) }.random()
        colors[nextColor] = colors[former]!!
        colors.remove(former)
    }

    fun save() {
    }
}