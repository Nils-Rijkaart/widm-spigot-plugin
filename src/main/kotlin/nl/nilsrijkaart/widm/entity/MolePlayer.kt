package nl.nilsrijkaart.widm.entity

import nl.nilsrijkaart.widm.game.GameRole
import java.util.UUID

data class MolePlayer(val uuid : UUID, var gamesHosted : Int, val wins : MutableMap<GameRole, Int>, val losses : MutableMap<GameRole, Int>)