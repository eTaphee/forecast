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

@Service
internal class ForecastQueryServiceImpl(
    private val forecastSyncRepository: ForecastSyncRepository
) : ForecastQueryService {

    override fun getVillageForecast(
        lat: Double,
        lng: Double
    ): List<ForecastInfoResponse> {
        val baseDateTime = TimeUtil.getReferenceDateTime(forecastTimeIntervals)
        val point = MapUtil.convertGpsToGrid(lat, lng).toPoint()

        // TODO, finished, canceled 확인
        forecastSyncRepository.findByBaseDateTimeAndLocation(baseDateTime, point)
            .let {
                return if (it.isPresent) {
                    it.get().forecastInfos.map { info -> info.toForecastInfoResponse() }
                } else {
                    emptyList()
                }
            }
    }
}