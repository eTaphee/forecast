package im.etap.forecast.application.api.service

import im.etap.forecast.application.api.controller.ForecastSyncRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange

/**
 * 기상예보 동기화 API
 */
interface ForecastSyncApi {

    /**
     * 단기예보 요청
     *
     * @param request 기상예보 동기화 요청
     */
    @PostExchange("/sync/village")
    fun requestVillageForecast(
        @RequestBody request: ForecastSyncRequest
    ): ResponseEntity<Void>
}