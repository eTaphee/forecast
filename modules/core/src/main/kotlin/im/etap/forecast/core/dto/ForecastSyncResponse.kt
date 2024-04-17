package im.etap.forecast.core.dto

import java.time.LocalDateTime

data class ForecastSyncResponse(
    val id: Long,
    val baseDateTime: LocalDateTime,
    val x: Double,
    val y: Double,
    val isFinished: Boolean
)