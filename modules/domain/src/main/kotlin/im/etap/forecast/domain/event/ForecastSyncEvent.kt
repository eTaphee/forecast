package im.etap.forecast.domain.event

import im.etap.forecast.domain.entity.ForecastSync
import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

/**
 * 기상예보 동기화 이벤트
 *
 * @param id Sync ID
 * @param baseDateTime 기준 시각
 * @param location 위치
 */
internal data class ForecastSyncEvent(
    val id: Long,
    val baseDateTime: LocalDateTime,
    val location: Point
) {
    companion object {
        fun from(entity: ForecastSync): ForecastSyncEvent {
            return ForecastSyncEvent(entity.id, entity.baseDateTime, entity.location)
        }
    }
}