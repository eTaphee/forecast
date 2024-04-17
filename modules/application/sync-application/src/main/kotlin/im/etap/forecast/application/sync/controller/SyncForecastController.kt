package im.etap.forecast.application.sync.controller

import im.etap.forecast.application.sync.service.VillageForecastSyncService
import im.etap.forecast.core.dto.ForecastSyncRequest
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
class SyncForecastController(private val syncService: VillageForecastSyncService) {

    /**
     * 단기예보 동기화
     *
     *  @param request 동기화 요청
     */
    @PostMapping("village")
    fun syncVillageForecast(@Valid @RequestBody request: ForecastSyncRequest): ResponseEntity<Void> {
        return if (syncService.saveVillageForecastSyncRequest(request).isFinished) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.accepted().build()
        }
    }
}