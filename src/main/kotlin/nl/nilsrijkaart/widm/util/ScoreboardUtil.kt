package nl.nilsrijkaart.widm.util

import nl.nilsrijkaart.widm.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot


class ScoreboardUtil {
    companion object {
        fun createScoreboard(player: Player) {
            val scoreboard = Bukkit.getScoreboardManager()?.newScoreboard!!
            val obj = scoreboard.registerNewObjective("widm", Criteria.DUMMY, "WIDM")
            obj.displaySlot = DisplaySlot.SIDEBAR
            obj.displayName = formattedMessage("&6Wie is de Mol?")

            val empty = obj.getScore(formattedMessage("&7"))
            empty.score = 16

            val players = obj.getScore(formattedMessage("&6Spelers:     &f"))
            players.score = 15
            val playerCount = scoreboard.registerNewTeam("widm_players")
            playerCount.addEntry("${ChatColor.BLUE} ${ChatColor.WHITE}")
            playerCount.prefix = formattedMessage("&7${Bukkit.getOnlinePlayers().size}")
            obj.getScore("${ChatColor.BLUE} ${ChatColor.WHITE}").score = 14

            val empty2 = obj.getScore(formattedMessage("&8"))
            empty2.score = 13

            val gameActive = obj.getScore(formattedMessage("&6Spel actief:     &f"))
            gameActive.score = 12
            val isActive = scoreboard.registerNewTeam("widm_active")
            isActive.addEntry("${ChatColor.RED} ${ChatColor.WHITE}")
            isActive.prefix = formattedMessage(if (GameManager.game != null) "&aJa" else "&cNee")
            obj.getScore("${ChatColor.RED} ${ChatColor.WHITE}").score = 11

            val empty3 = obj.getScore(formattedMessage("&9"))
            empty3.score = 10

            val role = obj.getScore(formattedMessage("&6Rol:     &f"))
            role.score = 9
            val roleTeam = scoreboard.registerNewTeam("widm_role")
            roleTeam.addEntry("${ChatColor.GREEN} ${ChatColor.WHITE}")
            roleTeam.prefix = formattedMessage("&7N/A")
            obj.getScore("${ChatColor.GREEN} ${ChatColor.WHITE}").score = 8

            player.scoreboard = scoreboard
        }

        fun updateScoreboard(player: Player) {
            val scoreboard = player.scoreboard
            val playerCount = scoreboard.getTeam("widm_players")
            playerCount?.prefix = formattedMessage("&7${Bukkit.getOnlinePlayers().size}")
            val isActive = scoreboard.getTeam("widm_active")
            isActive?.prefix = formattedMessage(if (GameManager.game != null) "&aJa" else "&cNee")
            val roleTeam = scoreboard.getTeam("widm_role")
            roleTeam?.prefix = formattedMessage("&f${GameManager.game?.getRole(player)?.displayName ?: "&7N/A"} ${if(GameManager.game?.isPeacekeeper(player) == true) "&7(PK)" else ""}")
        }
    }


}