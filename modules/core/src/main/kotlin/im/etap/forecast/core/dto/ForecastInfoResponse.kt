package im.etap.forecast.core.dto

import java.time.LocalDate
import java.time.LocalTime

/**
 * 기상예보 정보
 */
data class ForecastInfoResponse(
    val category: String,
    val forecastDate: LocalDate,
    val forecastTime: LocalTime,
    val forecastValue: String,
    val baseDate: LocalDate,
    val baseTime: LocalTime
)