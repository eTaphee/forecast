package im.etap.forecast.domain.service

import im.etap.forecast.core.dto.ForecastSyncRequest
import im.etap.forecast.core.dto.ForecastSyncResponse

interface ForecastSyncService {

    fun saveVillageForecastSyncRequest(request: ForecastSyncRequest): ForecastSyncResponse
}