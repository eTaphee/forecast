package im.etap.forecast.application.api.service

import im.etap.forecast.application.api.dto.ForecastInfoResponse
import im.etap.forecast.core.map.MapGrid
import im.etap.forecast.core.map.MapUtil
import im.etap.forecast.core.time.TimeUtil
import im.etap.forecast.core.time.TimeUtil.Companion.generateTimeIntervals
import im.etap.forecast.domain.repository.ForecastSyncRepository
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.springframework.stereotype.Service

/**
 * 기상예보 조회 서비스
 *
 * @param forecastSyncRepository 기상예보 동기화 레포지토리
 */
@Service
class ForecastQueryService(
    private val forecastSyncRepository: ForecastSyncRepository
) {
    companion object {
        // TODO: 시간, 간격 상수, 중복
        private val forecastTimeIntervals = generateTimeIntervals("0210", 3)
    }

    /**
     * 단기예보 조회
     *
     * @param lat 위도(33~38)
     * @param lng 경도(124~132)
     *
     * @return 단기예보 정보
     */
    fun getVillageForecast(lat: Double, lng: Double): List<ForecastInfoResponse> {
        val referenceDateTime = TimeUtil.getReferenceDateTime(forecastTimeIntervals)
        val point = MapUtil.convertGpsToGrid(lat, lng).toPoint()

        // TODO, finished, canceled 확인
        forecastSyncRepository.findByBaseDateTimeAndLocation(referenceDateTime, point)
            .let {
                return if (it.isPresent) {
                    it.get().forecastInfos.map(ForecastInfoResponse::from)
                } else {
                    emptyList()
                }
            }
    }

    // TODO: 중복
    fun MapGrid.toPoint(): Point {
        val geometryFactory = GeometryFactory()
        val coordinate = Coordinate(this.x, this.y)
        return geometryFactory.createPoint(coordinate)
    }
}