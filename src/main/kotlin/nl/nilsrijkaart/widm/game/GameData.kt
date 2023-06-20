package nl.nilsrijkaart.widm.game

import java.util.UUID

data class GameData(
    val id: Int,
    val name: String,
    val hosts: List<UUID>,
    val slots: List<GameSlot>,
    val rules: Map<GameRule, Boolean>,
)

data class LocationData(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float
)