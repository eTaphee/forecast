package im.etap.forecast.application.api.dto

import im.etap.forecast.domain.entity.ForecastInfo
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
) {
    // TODO: baseTime 10분 차이 보정
    companion object {
        fun from(forecastInfo: ForecastInfo): ForecastInfoResponse {
            return ForecastInfoResponse(
                category = forecastInfo.category,
                forecastDate = forecastInfo.forecastDate,
                forecastTime = forecastInfo.forecastTime,
                forecastValue = forecastInfo.forecastValue,
                baseDate = forecastInfo.forecastSync.baseDateTime.toLocalDate(),
                baseTime = forecastInfo.forecastSync.baseDateTime.toLocalTime()
            )
        }
    }
}