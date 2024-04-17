package im.etap.forecast.domain

import im.etap.forecast.core.dto.ForecastInfoResponse
import im.etap.forecast.core.dto.ForecastSyncRequest
import im.etap.forecast.core.dto.ForecastSyncResponse
import im.etap.forecast.core.map.MapGrid
import im.etap.forecast.core.map.MapUtil
import im.etap.forecast.core.time.TimeUtil
import im.etap.forecast.domain.entity.ForecastInfo
import im.etap.forecast.domain.entity.ForecastSync
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point

/**
 * 확장함수 및 전역변수..
 */

internal val forecastTimeIntervals = TimeUtil.generateTimeIntervals("0210", 3)

internal fun MapGrid.toPoint(): Point {
    val geometryFactory = GeometryFactory()
    val coordinate = Coordinate(this.x, this.y)
    return geometryFactory.createPoint(coordinate)
}

internal fun ForecastSyncRequest.toPoint(): Point {
    return MapUtil.convertGpsToGrid(lat, lng).toPoint()
}

internal fun ForecastSync.toForecastSyncResponse(): ForecastSyncResponse {
    return ForecastSyncResponse(
        id,
        baseDateTime,
        location.x,
        location.y,
        isFinished
    )
}

internal fun ForecastInfo.toForecastInfoResponse(): ForecastInfoResponse {
    return ForecastInfoResponse(
        category,
        forecastDate,
        forecastTime,
        forecastValue,
        forecastSync.baseDateTime.toLocalDate(),
        forecastSync.baseDateTime.toLocalTime()
    )
}