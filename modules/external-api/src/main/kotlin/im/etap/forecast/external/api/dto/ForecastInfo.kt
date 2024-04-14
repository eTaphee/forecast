package im.etap.forecast.external.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalTime

data class ForecastInfo(
    @JsonFormat(pattern = "yyyyMMdd")
    val baseDate: LocalDate,

    @JsonFormat(pattern = "HHmm")
    val baseTime: LocalTime,

    val category: ForecastCategory,

    @JsonFormat(pattern = "yyyyMMdd")
    @JsonProperty("fcstDate")
    val forecastDate: LocalDate,

    @JsonFormat(pattern = "HHmm")
    @JsonProperty("fcstTime")
    val forecastTime: LocalTime,

    @JsonProperty("fcstValue")
    val forecastValue: String,

    val nx: Int,
    val ny: Int
)
