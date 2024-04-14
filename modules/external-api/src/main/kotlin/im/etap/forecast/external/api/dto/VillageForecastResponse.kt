package im.etap.forecast.external.api.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import im.etap.forecast.external.api.dto.deserializer.VillageForecastResponseDeserializer

/**
 * 단기예보 조회 응답
 */
@JsonDeserialize(using = VillageForecastResponseDeserializer::class)
data class VillageForecastResponse(
    val resultCode: String,
    val resultMessage: String,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int,
    val items: List<ForecastInfo>
)
