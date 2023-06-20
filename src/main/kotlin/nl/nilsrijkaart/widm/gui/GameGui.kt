package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.events.ChatEvent
import nl.nilsrijkaart.widm.game.Game
import nl.nilsrijkaart.widm.game.GameManager
import nl.nilsrijkaart.widm.service.GameService
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class GameGui {

    companion object {
        fun open(game: Game, player: Player) {
            val inventory = MoleGui("Game ${game.name}", 9)
            inventory.addItem(0, Material.SKELETON_SKULL, "&6Spelers", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GamePlayerGui.open(game, player)
            }

            inventory.addItem(1, Material.BOOK, "&6Regels", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameRuleGui.openGui(game, player)
            }

            inventory.addItem(2, Material.STICK, "&6Host toevoegen", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                player.sendMessage("§cTyp de naam van de speler in de chat.")
                player.closeInventory()
                ChatEvent.hookChat(player.uniqueId) { event ->
                    event.isCancelled = true

                    val target = Bukkit.getPlayer(event.message)
                    if (target == null) {
                        player.sendMessage("§cDeze speler bestaat niet.")
                        return@hookChat
                    }

                    if (game.hosts.contains(target.uniqueId)) {
                        player.sendMessage("§cDeze speler is al host.")
                        return@hookChat
                    }

                    game.hosts.add(target.uniqueId)
                    game.save()
                    player.sendMessage("§aDe speler is toegevoegd als host.")
                    target.sendMessage("§aJe bent toegevoegd als host aan een potje.")

                }
            }

            inventory.addItem(3, Material.BIRCH_SIGN, "&aNaam van potje instellen", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                player.sendMessage("§cTyp de naam van het potje in de chat.")
                player.closeInventory()
                ChatEvent.hookChat(player.uniqueId) { event ->
                    event.isCancelled = true
                    game.name = event.message
                    game.save()
                    player.sendMessage("§aDe naam van het potje is ingesteld.")
                }
            }


            inventory.addItem(4, Material.GREEN_WOOL, "&aPotje starten", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameStartGui.open(player, game)
            }

            inventory.addItem(5, Material.RED_WOOL, "&cPotje stoppen", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameManager.game = null
                player.closeInventory()
                Bukkit.broadcastMessage("§cHet potje is afgelopen.")
            }

            inventory.addItem(6, Material.BARRIER, "&cPotje verwijderen", listOf()) {clickEvent ->
                clickEvent.isCancelled = true
                GameService.deleteGame(game)
                player.closeInventory()
                Bukkit.broadcastMessage("§cHet potje is verwijderd.")
            }


            inventory.addItem(8, Material.LEVER, "&cTerug", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameListGui.open(player)
            }

            inventory.open(player)
        }
    }
}