package im.etap.forecast.application.sync.dto

import im.etap.forecast.core.map.MapGrid
import im.etap.forecast.core.map.MapUtil
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point

/**
 * 기상예보 동기화 요청
 *
 * @param lat 위도
 * @param lng 경도
 */
data class ForecastSyncRequest(val lat: Double, val lng: Double)

fun ForecastSyncRequest.getPoint(): Point {
    return MapUtil.convertGpsToGrid(lat, lng).toPoint()
}

fun MapGrid.toPoint(): Point {
    val geometryFactory = GeometryFactory()
    val coordinate = Coordinate(this.x, this.y)
    return geometryFactory.createPoint(coordinate)
}