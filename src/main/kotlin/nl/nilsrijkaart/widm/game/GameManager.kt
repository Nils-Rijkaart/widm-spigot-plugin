package nl.nilsrijkaart.widm.game

import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit

class GameManager {
    companion object {
        var game: Game? = null

        fun requestStart(game: Game): Boolean {
            if (this.game != null) {
                if (Bukkit.getOnlinePlayers().any {
                        this.game!!.hosts.contains(it)
                    }) {
                    return false;
                }
            }

            this.game = game
            return true
        }
    }
}