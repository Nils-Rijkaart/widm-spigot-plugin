package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.game.GameRole
import org.bukkit.Material
import org.bukkit.entity.Player

class StatisticGui {
    companion object {
        fun open(player : Player) {
            val inventory = MoleGui("Statistieken", 18)
            val molePlayer = nl.nilsrijkaart.widm.service.PlayerService.getPlayer(player.uniqueId)
            val hostedGames = molePlayer.gamesHosted
            val wins = molePlayer.wins.values.sum()
            val losses = molePlayer.losses.values.sum()
            val winPercentage = if (wins == 0 && losses == 0) {
                0.0
            } else {
                (wins.toDouble() / (wins + losses)) * 100
            }

            val worstRole = molePlayer.wins.minByOrNull { it.value }?.key?.displayName ?: "Geen"
            val bestRole = molePlayer.wins.maxByOrNull { it.value }?.key?.displayName ?: "Geen"

            val generalLore = listOf("&aGehoste potjes: &2$hostedGames", "&aGewonnen potjes: &2$wins", "&aVerloren potjes: &2$losses", "&aWin percentage: &2$winPercentage%", "&aBeste rol: &2$bestRole", "&aSlechtste rol: &2$worstRole")

            inventory.addItem(-1, Material.BLAZE_POWDER, "&6Algemene statistieken", generalLore) {
                it.isCancelled = true
            }

            GameRole.values().forEach {
                val w = molePlayer.wins[it] ?: 0
                val l = molePlayer.losses[it] ?: 0
                val wp = if (wins == 0 && losses == 0) {
                    0.0
                } else {
                    (wins.toDouble() / (wins + losses)) * 100
                }

                val lore = listOf("&aGewonnen potjes: &2$w", "&aVerloren potjes: &2$l", "&aWin percentage: &2$wp%")
                inventory.addItem(-1, it.item, "&6${it.displayName}", lore) { c ->
                    c.isCancelled = true
                }
            }

            inventory.addItem(17, Material.BARRIER, "&cSluiten", listOf("&cSluit dit menu.")) {
                it.isCancelled = true
                player.closeInventory()
            }

            inventory.open(player)
        }
    }
}