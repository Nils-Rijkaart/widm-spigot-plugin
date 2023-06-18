package nl.nilsrijkaart.widm.game

import org.bukkit.Location
import java.util.*

data class GameSlot(var color: GameColor, var role: GameRole, var location: Location? = null, var player: UUID? = null)