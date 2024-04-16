package im.etap.forecast.application.sync.service

import im.etap.forecast.application.sync.dto.ForecastSyncRequest
import im.etap.forecast.application.sync.dto.ForecastSyncResponse
import im.etap.forecast.application.sync.dto.getPoint
import im.etap.forecast.application.sync.event.ForecastSyncEvent
import im.etap.forecast.core.time.TimeUtil
import im.etap.forecast.core.time.TimeUtil.Companion.generateTimeIntervals
import im.etap.forecast.domain.entity.ForecastSync
import im.etap.forecast.domain.repository.ForecastSyncRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 기상예보 동기화 서비스
 */
@Service
class ForecastSyncService(
    private val forecastSyncRepository: ForecastSyncRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {
    companion object {
        // TODO: 시간, 간격 상수
        private val forecastTimeIntervals = generateTimeIntervals("0210", 3)
    }

    /**
     * 단기예보 동기화 요청
     *
     * @param request 동기화 요청
     */
    @Transactional
    fun saveVillageForecastSyncRequest(request: ForecastSyncRequest): ForecastSyncResponse {
        val referenceDateTime = TimeUtil.getReferenceDateTime(forecastTimeIntervals)
        val point = request.getPoint()

        forecastSyncRepository.findByBaseDateTimeAndLocation(referenceDateTime, point)
            .orElseGet {
                forecastSyncRepository.save(ForecastSync(referenceDateTime, point))
                    .also { eventPublisher.publishEvent(ForecastSyncEvent.from(it)) }
            }
            .let { return ForecastSyncResponse.from(it) }
    }
}