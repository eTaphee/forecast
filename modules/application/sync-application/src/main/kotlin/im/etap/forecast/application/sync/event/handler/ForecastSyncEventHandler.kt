package im.etap.forecast.application.sync.event.handler

import im.etap.forecast.application.core.exception.ErrorCode.FORECAST_SYNC_NOT_FOUND
import im.etap.forecast.application.core.exception.ForecastException
import im.etap.forecast.application.sync.event.ForecastSyncEvent
import im.etap.forecast.domain.entity.ForecastSync
import im.etap.forecast.domain.repository.ForecastSyncRepository
import im.etap.forecast.external.api.dto.ForecastInfo
import im.etap.forecast.external.api.service.ForecastApi
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT
import org.springframework.transaction.event.TransactionalEventListener
import java.time.format.DateTimeFormatter

/**
 * 기상예보 동기화 이벤트 핸들러
 */
@Service
class ForecastSyncEventHandler(
    @Value("\${api-data-go-kr.serviceKey}")
    private val serviceKey: String,
    private val forecastApi: ForecastApi,
    private val forecastSyncRepository: ForecastSyncRepository
) {
    private val logger = KotlinLogging.logger {}

    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")
        private val timeFormat = DateTimeFormatter.ofPattern("HHmm")
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW, noRollbackFor = [Exception::class])
    @TransactionalEventListener(
        classes = [ForecastSyncEvent::class],
        phase = AFTER_COMMIT
    )
    fun handle(event: ForecastSyncEvent) {
        val forecastSync = forecastSyncRepository.findById(event.id)
            .orElseThrow { throw ForecastException(FORECAST_SYNC_NOT_FOUND) }

        try {
            getForecastInfos(event)
                .map { it.toEntity(forecastSync) }
                .run { forecastSync.addForecastInfos(this) }
        } catch (e: Exception) {
            forecastSync.fail()
            throw e
        }
    }

    private fun getForecastInfos(event: ForecastSyncEvent): List<ForecastInfo> {
        val baseDate = event.baseDateTime.toLocalDate().format(dateFormat)
        val baseTime = event.baseDateTime.toLocalTime().withMinute(0).format(timeFormat)
        val x = event.location.x.toInt()
        val y = event.location.y.toInt()

        var pageNo = 1
        var totalCount = 0
        val list = mutableListOf<ForecastInfo>()

        logger.info { "Start village forecast api $event" }

        try {
            do {
                forecastApi.getVillageForecast(
                    serviceKey,
                    pageNo++,
                    1000,
                    baseDate,
                    baseTime,
                    x,
                    y
                )
                    .run {
                        totalCount = this.totalCount
                        list.addAll(this.items)
                    }
            } while (list.size < totalCount)
        } catch (e: Exception) {
            logger.error(e) { "Failed to get village forecast $event" }
            throw e
        }

        logger.info { "End village forecast api $event, size=${list.size}" }

        return list
    }

    private fun ForecastInfo.toEntity(forecastSync: ForecastSync): im.etap.forecast.domain.entity.ForecastInfo {
        return im.etap.forecast.domain.entity.ForecastInfo(
            category = this.category.toString(),
            forecastDate = this.forecastDate,
            forecastTime = this.forecastTime,
            forecastValue = this.forecastValue,
            forecastSync = forecastSync
        )
    }
}