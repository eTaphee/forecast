package im.etap.forecast.application.sync.controller

import im.etap.forecast.application.core.exception.ErrorCode
import im.etap.forecast.application.core.exception.ForecastException
import im.etap.forecast.application.sync.dto.ForecastSyncRequest
import im.etap.forecast.application.sync.service.ForecastSyncService
import im.etap.forecast.domain.entity.ForecastSync
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 기상예보 동기화 컨트롤러
 */
@RestController
@RequestMapping("/sync")
class ForecastSyncController(private val forecastSyncService: ForecastSyncService) {

    /**
     * 단기예보 동기화
     *
     *  @param request 동기화 요청
     */
    @PostMapping("village")
    fun syncVillageForecast(@Valid @RequestBody request: ForecastSyncRequest): ResponseEntity<Void> {
        return if (forecastSyncService.saveVillageForecastSyncRequest(request).isFinished) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.accepted().build()
        }
    }
}