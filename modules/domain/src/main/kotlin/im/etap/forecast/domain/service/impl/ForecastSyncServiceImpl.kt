package im.etap.forecast.domain.service.impl

import im.etap.forecast.core.dto.ForecastSyncRequest
import im.etap.forecast.core.dto.ForecastSyncResponse
import im.etap.forecast.core.time.TimeUtil
import im.etap.forecast.domain.entity.ForecastSync
import im.etap.forecast.domain.event.ForecastSyncEvent
import im.etap.forecast.domain.forecastTimeIntervals
import im.etap.forecast.domain.repository.ForecastSyncRepository
import im.etap.forecast.domain.service.ForecastSyncService
import im.etap.forecast.domain.toForecastSyncResponse
import im.etap.forecast.domain.toPoint
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
internal class ForecastSyncServiceImpl(
    private val forecastSyncRepository: ForecastSyncRepository,
    private val eventPublisher: ApplicationEventPublisher
) : ForecastSyncService {

    @Transactional
    override fun saveVillageForecastSyncRequest(request: ForecastSyncRequest): ForecastSyncResponse {
        val baseDateTime = TimeUtil.getReferenceDateTime(
            forecastTimeIntervals,
            LocalDateTime.now().minusMinutes(10) // 정각에 생성, 10분 후 갱신
        )
        val point = request.toPoint()

        forecastSyncRepository.findByBaseDateTimeAndLocation(baseDateTime, point)
            .orElseGet {
                forecastSyncRepository.save(ForecastSync(baseDateTime, point))
                    .also { eventPublisher.publishEvent(ForecastSyncEvent.from(it)) }
            }
            .let { return it.toForecastSyncResponse() }
    }
}