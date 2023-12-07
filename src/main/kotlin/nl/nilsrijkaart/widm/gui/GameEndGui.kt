package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.service.PlayerService
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class GameEndGui {
    companion object {
        fun open(game: Game, player: Player) {
            val inventory = MoleGui(
                "Selecteer de winnaar.",
                18
            )
            game.slots.forEach {
                if (it.player != null) {
                    val roleLore = listOf(
                        "&aDeze speler is een",
                        "&2${it.role.displayName}",
                        "&aDit team wint als",
                        "&aje deze speler selecteert.",
                    )
                    inventory.addSkull(
                        -1,
                        Bukkit.getOfflinePlayer(it.player!!).name ?: player.name,
                        "&6${Bukkit.getOfflinePlayer(it.player!!).name ?: player.name}",
                        roleLore,
                        false
                    ) { clickEvent ->
                        clickEvent.isCancelled = true
                        GameManager.game = null
                        player.closeInventory()
                        Bukkit.broadcastMessage("§cHet potje is afgelopen. De winnaar is ${it.color.displayName}! (${it.role.displayName})")
                        game.slots.forEach { slot ->
                            if (slot.player != null) {
                                val molePlayer = PlayerService.getPlayer(slot.player!!)
                                if(slot.role == it.role) {
                                    val wins = molePlayer.wins[it.role] ?: 0
                                    molePlayer.wins[it.role] = wins + 1
                                } else {
                                    val losses = molePlayer.losses[it.role] ?: 0
                                    molePlayer.losses[it.role] = losses + 1
                                }
                                PlayerService.updatePlayer(molePlayer)
                            }
                        }
                        game.hosts.forEach { h ->
                            val molePlayer = PlayerService.getPlayer(h)
                            val hosted = molePlayer.gamesHosted + 1
                            molePlayer.gamesHosted = hosted
                            PlayerService.updatePlayer(molePlayer)
                        }
                    }
                }
            }

            inventory.addItem(16, org.bukkit.Material.BARRIER, "&cPotje annuleren", listOf(
                "&cDit stopt het potje",
                "&czonder statistiek wijzigingen."
            )) { clickEvent ->
                clickEvent.isCancelled = true
                GameManager.game = null
                player.closeInventory()
            }

            inventory.addItem(17, org.bukkit.Material.STICK, "&cTerug", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameGui.open(game, player)
            }

            inventory.open(player)
        }
    }
}