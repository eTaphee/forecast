package im.etap.forecast.application.api.controller

import im.etap.forecast.client.ForecastSyncClient
import im.etap.forecast.core.dto.ForecastSyncRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 기상예보 동기화 컨트롤러
 **/
@RestController
@RequestMapping("/sync")
class ForecastSyncController(
    private val forecastSyncClient: ForecastSyncClient
) {

    /**
     * 단기예보 요청
     *
     * @param request 기상예보 동기화 요청
     */
    @PostMapping("village")
    fun syncVillageForecast(@RequestBody request: ForecastSyncRequest): ResponseEntity<Void> {
        return ResponseEntity
            .status(forecastSyncClient.requestVillageForecast(request).statusCode)
            .build()
    }
}
