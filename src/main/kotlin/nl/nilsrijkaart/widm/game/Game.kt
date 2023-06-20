package nl.nilsrijkaart.widm.game

import nl.nilsrijkaart.widm.service.GameService
import nl.nilsrijkaart.widm.util.formattedMessage
import nl.nilsrijkaart.widm.util.locationDataToSpigotLocation
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class Game(val id: Int, var name: String) {

    var hosts: MutableList<UUID> = mutableListOf()

    //default settings, otherwise loaded from json
    var rules = mutableMapOf(
        GameRule.PVP to true,
        GameRule.MSG to false,
        GameRule.PK_DEATHNOTE to false,
        GameRule.AUTO_UTIL to true,
        GameRule.DEATH_MESSAGE to true,
        GameRule.COLORS_COMMAND to true
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

    fun assignRandomRole(player: Player): Boolean {
        val gameSlot = slots.find { it.player == null }
        if (gameSlot != null) {
            gameSlot.player = player.uniqueId
            save()
            return true
        }
        return false
    }

    fun start() {
        if (GameManager.requestStart(this)) {
            this.slots.forEach {
                if (it.player != null) {
                    val player = Bukkit.getPlayer(it.player!!)
                    if (it.location != null) {
                        player?.teleport(locationDataToSpigotLocation(it.location!!))
                    } else {
                        hosts.forEach { host ->
                            Bukkit.getPlayer(host)?.sendMessage("§c${Bukkit.getPlayer(it.player!!)?.name} heeft geen spawnpoint!")
                        }
                    }
                    
                    player?.sendMessage(formattedMessage("&6Het potje is begonnen. Je bent de kleur &${it.color.code}${it.color.displayName}&6. Je rol is &7${it.role.displayName}&6. Succes!"))
                    player?.sendTitle(formattedMessage("&c${it.role.displayName}"), formattedMessage("Je bent de kleur &${it.color.code}${it.color.displayName}"), 10, 200, 20)
                }
            }

            hosts.forEach {
                Bukkit.getPlayer(it)?.sendMessage(formattedMessage("&6De startprocedure is voltooid."))
            }
        } else {
            hosts.forEach {
                Bukkit.getPlayer(it)?.sendMessage(formattedMessage("&cHet potje kan niet gestart worden omdat er een actief potje is."))
            }
        }
    }

    fun save() {
        GameService.saveGame(this)
    }
}