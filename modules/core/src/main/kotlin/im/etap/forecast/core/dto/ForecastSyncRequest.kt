package im.etap.forecast.core.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class ForecastSyncRequest(
    @field:Min(33)
    @field:Max(38)
    val lat: Double,
    @field:Min(124)
    @field:Max(132)
    val lng: Double
)
