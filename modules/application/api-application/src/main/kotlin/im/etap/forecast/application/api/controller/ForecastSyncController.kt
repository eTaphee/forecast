package im.etap.forecast.application.api.controller

import im.etap.forecast.application.api.service.ForecastSyncApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 기상예보 동기화 컨트롤러
 *
 * @param forecastSyncApi 동기화 API
 */
@RestController
@RequestMapping("/sync")
class ForecastSyncController(
    private val forecastSyncApi: ForecastSyncApi
) {

    /**
     * 단기예보 요청
     *
     * @param request 기상예보 동기화 요청
     */
    @PostMapping("village")
    fun syncVillageForecast(@RequestBody request: ForecastSyncRequest): ResponseEntity<Void> {
        return ResponseEntity
            .status(forecastSyncApi.requestVillageForecast(request).statusCode)
            .build()
    }
}

/**
 * 기상예보 동기화 요청
 *
 * @param lat 위도(33~38)
 * @param lng 경도(124~132)
 */
data class ForecastSyncRequest(
    val lat: Double,
    val lng: Double
)