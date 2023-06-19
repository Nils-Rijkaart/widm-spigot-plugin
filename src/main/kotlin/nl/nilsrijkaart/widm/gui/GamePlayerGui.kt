package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.Game
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class GamePlayerGui {

    companion object {

        fun open(game: Game, player: Player) {
            val inventory = MoleGui("&6Spelers - ${game.name}", 27)

            game.slots.forEach { entry ->

                val lore = listOf(
                    "&6Rol: &7${entry.role.name}",
                    "&6Kleur: &7${entry.color.displayName}",
                    "&6Locatie: &7${
                        if (entry.location == null) "Niet ingesteld" else "Ingesteld"
                    }",
                    "&6Speler: &7${
                        if (entry.player == null) "Willekeurig" else "${Bukkit.getOfflinePlayer(entry.player!!).name}"
                    }"
                )

                inventory.addItem(
                    -1,
                    entry.color.material,
                    "&${entry.color.code}${entry.color.displayName}",
                    lore
                ) { clickEvent ->
                    clickEvent.isCancelled = true
                    GamePlayerEditGui.open(player, game, entry)
                }

            }

            inventory.addItem(
                -1,
                Material.STICK,
                "&aNieuwe rol toevoegen",
                listOf()
            ) { clickEvent ->
                clickEvent.isCancelled = true
                game.addRole()
                game.save()
                open(game, player)
            }


            inventory.addItem(26, Material.LEVER, "&cTerug", listOf()) { clickEvent ->
                clickEvent.isCancelled = true
                GameGui.open(game, player)
            }

            inventory.open(player)
        }
    }
}