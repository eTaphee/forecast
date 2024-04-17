package im.etap.forecast.domain.service

import im.etap.forecast.core.dto.ForecastInfoResponse

interface ForecastQueryService {

    fun getVillageForecast(lat: Double, lng: Double): List<ForecastInfoResponse>
}