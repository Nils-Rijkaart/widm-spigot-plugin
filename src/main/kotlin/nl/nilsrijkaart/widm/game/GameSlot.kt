package nl.nilsrijkaart.widm.game

import java.util.*

data class GameSlot(
    var color: GameColor,
    var role: GameRole,
    var location: LocationData? = null,
    var player: UUID? = null,
    var soulBounds: List<GameColor>? = listOf()
)