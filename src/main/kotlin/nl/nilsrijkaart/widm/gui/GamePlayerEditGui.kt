package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.game.Game
import nl.nilsrijkaart.widm.game.GameSlot
import nl.nilsrijkaart.widm.game.LocationData
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class GamePlayerEditGui {

    companion object {

        fun open(player: Player, game: Game, gameSlot: GameSlot) {
            val inventory = MoleGui("&6Gameslot - ${gameSlot.color.name}", 9)


            // role item
            inventory.addItem(-1, gameSlot.role.item, "&6Rol: &7${gameSlot.role.displayName}", listOf()) {
                it.isCancelled = true
                // get next role:
                val nextRole = gameSlot.role.next()
                gameSlot.role = nextRole
                game.updateSlot(gameSlot)
                game.save()
                open(player, game, gameSlot)
            }

            inventory.addItem(
                -1,
                gameSlot.color.material,
                "&${gameSlot.color.code}${gameSlot.color.displayName}",
                listOf()
            ) {
                it.isCancelled = true
                game.updateColor(gameSlot.color)
                game.save()
                open(player, game, gameSlot)
            }

            inventory.addItem(
                -1,
                Material.COMPASS,
                "&aZet de locatie",
                listOf("Verander de locatie naar je huidige plek")
            ) {
                gameSlot.location = LocationData(
                    player.location.world?.name ?: "world",
                    player.location.x,
                    player.location.y,
                    player.location.z,
                    player.location.yaw,
                    player.location.pitch
                )
                game.updateSlot(gameSlot)
                game.save()
                player.closeInventory()
                player.sendMessage(formattedMessage("&6Locatie is aangepast naar je huidige locatie."))
            }

            val tp = gameSlot.player
            val name = if (tp == null) {
                "Willekeurig"
            } else {
                Bukkit.getOfflinePlayer(tp).name
            }

            inventory.addItem(-1, Material.CREEPER_HEAD, "&6Speler: &7$name", listOf()) {
                it.isCancelled = true
                player.sendMessage(formattedMessage("&6Typ de naam van de speler in de chat (hoofdlettergevoelig). Typ random om het willekeurig te maken."))
                player.closeInventory()
                ChatEvent.hookChat(player.uniqueId) { event ->
                    val targetName = event.message
                    if (targetName.lowercase() == "random") {
                        gameSlot.player = null
                        game.updateSlot(gameSlot)
                        game.save()
                        player.sendMessage(formattedMessage("&6Speler is nu random"))
                        return@hookChat
                    }

                    val targetPlayer = Bukkit.getOfflinePlayers().firstOrNull { name -> name.name == targetName }
                    if (targetPlayer != null) {
                        gameSlot.player = targetPlayer.uniqueId
                        game.updateSlot(gameSlot)
                        game.save()
                        player.sendMessage(formattedMessage("&6Speler is aangepast naar &7${targetPlayer.name}"))
                    } else {
                        player.sendMessage(formattedMessage("&6Speler &7${targetName} &6bestaat niet."))
                    }
                }
            }

            val soulBoundLore = if (gameSlot.soulBounds?.isEmpty() == true || gameSlot.soulBounds == null) {
                listOf("&7Geen soulbounds")
            } else {
                gameSlot.soulBounds!!.map { soulbound ->
                    "&7- &${soulbound.code}${soulbound.displayName}"
                }
            }

            inventory.addItem(-1, Material.REDSTONE, "&6Soulbounds", soulBoundLore) {
                SoulBoundGui.open(game, player, gameSlot)
            }


            inventory.addItem(-1, Material.BARRIER, "&cVerwijder de rol", listOf("Verwijder de rol van deze speler")) {
                game.slots.remove(gameSlot)
                game.save()
                GamePlayerGui.open(game, player)
            }

            inventory.addItem(8, Material.LEVER, "&cTerug", listOf()) {
                player.closeInventory()
                GamePlayerGui.open(game, player)
            }

            inventory.open(player)
        }

    }

}