package im.etap.forecast.application.api.controller

import im.etap.forecast.core.dto.ForecastInfoResponse
import im.etap.forecast.domain.service.ForecastQueryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 기상예보 조회 컨트롤러
 */
@RestController
@RequestMapping("/forecast")
class ForecastQueryController(
    private val forecastQueryService: ForecastQueryService
) {

    /**
     * 단기예보 조회
     *
     * @param lat 위도(33~38)
     * @param lng 경도(124~132)
     *
     * @return 단기예보 정보 (없을 경우 NO_CONTENT)
     */
    @GetMapping("/village")
    fun getVillageForecast(
        lat: Double = 0.0,
        lng: Double = 0.0
    ): ResponseEntity<List<ForecastInfoResponse>> {
        forecastQueryService.getVillageForecast(lat, lng)
            .let {
                val status = if (it.isEmpty()) {
                    HttpStatus.NO_CONTENT
                } else {
                    HttpStatus.OK
                }
                return ResponseEntity.status(status).body(it)
            }
    }
}
