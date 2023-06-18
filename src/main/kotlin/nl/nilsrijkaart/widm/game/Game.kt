package nl.nilsrijkaart.widm.game

import nl.nilsrijkaart.widm.service.GameService
import org.bukkit.entity.Player

class Game(val id: Int, var name: String) {

    var hosts: List<Player> = listOf()

    //default settings, otherwise loaded from json
    var rules = mutableMapOf(
        GameRule.PVP to true,
        GameRule.MSG to false,
        GameRule.PK_DEATHNOTE to false
    )

    var slots = mutableListOf<GameSlot>()

    fun addRole() {
        val gameSlot = GameSlot(GameColor.values().filter {
            slots.none { slot -> slot.color == it }
        }.random(), GameRole.PLAYER)
        slots.add(gameSlot)
        save()
    }

    fun updateSlot(gameSlot: GameSlot) {
        slots[slots.indexOfFirst { it.color == gameSlot.color }] = gameSlot
        save()
    }

    fun updateRole(color: GameColor, role: GameRole) {
        slots.find { it.color == color }?.role = role
        save()
    }

    fun updateColor(former: GameColor) {
        slots.find { it.color == former }?.color = GameColor.values().filter {
            slots.none { slot -> slot.color == it }
        }.random()
        save()
    }

    fun getRole(player: Player): GameRole? {
        return slots.find { it.player == player.uniqueId }?.role
    }

    fun start() {
        GameManager.requestStart(this)
    }

    fun save() {
        GameService.saveGame(this)
    }
}