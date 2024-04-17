package im.etap.forecast.domain.service.impl

import im.etap.forecast.core.dto.ForecastInfoResponse
import im.etap.forecast.core.map.MapUtil
import im.etap.forecast.core.time.TimeUtil
import im.etap.forecast.domain.forecastTimeIntervals
import im.etap.forecast.domain.repository.ForecastSyncRepository
import im.etap.forecast.domain.service.ForecastQueryService
import im.etap.forecast.domain.toForecastInfoResponse
import im.etap.forecast.domain.toPoint
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
internal class ForecastQueryServiceImpl(
    private val forecastSyncRepository: ForecastSyncRepository
) : ForecastQueryService {

    override fun getVillageForecast(
        lat: Double,
        lng: Double
    ): List<ForecastInfoResponse> {
        val baseDateTime = TimeUtil.getReferenceDateTime(
            forecastTimeIntervals,
            LocalDateTime.now().minusMinutes(10) // 정각에 생성, 10분 후 갱신
        )
        val point = MapUtil.convertGpsToGrid(lat, lng).toPoint()

        forecastSyncRepository.findAvailableForecastSync(baseDateTime, point)
            .let {
                return if (it.isPresent) {
                    it.get().forecastInfos.map { info -> info.toForecastInfoResponse() }
                } else {
                    emptyList()
                }
            }
    }
}