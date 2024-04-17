package im.etap.forecast.application.sync.service

import im.etap.forecast.core.dto.ForecastSyncRequest
import im.etap.forecast.core.dto.ForecastSyncResponse
import im.etap.forecast.domain.service.ForecastSyncService
import org.springframework.stereotype.Service


/**
 * 기상예보 동기화 서비스
 */
@Service
class VillageForecastSyncService(
    private val forecastSyncService: ForecastSyncService
) {

    /**
     * 단기예보 동기화 요청
     *
     * @param request 동기화 요청
     */
    fun saveVillageForecastSyncRequest(request: ForecastSyncRequest): ForecastSyncResponse {
        return forecastSyncService.saveVillageForecastSyncRequest(request)
    }
}