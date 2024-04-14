package im.etap.forecast.core.map

import kotlin.math.*

class MapUtil {
    companion object {
        private const val RE: Double = 6371.00877 // 지구 반경(km)
        private const val GRID: Double = 5.0 // 격자 간격(km)
        private const val SLAT1: Double = 30.0 // 투영 위도1(degree)
        private const val SLAT2: Double = 60.0 // 투영 위도2(degree)
        private const val OLON: Double = 126.0 // 기준점 경도(degree)
        private const val OLAT: Double = 38.0 // 기준점 위도(degree)
        private const val XO: Double = 43.0 // 기준점 X좌표(GRID)
        private const val YO: Double = 136.0 // 기1준점 Y좌표(GRID)

        private const val RE_GRID = RE / GRID
        private const val DEG_RAD = Math.PI / 180.0
        private const val SLAT1_RAD = SLAT1 * DEG_RAD
        private const val SLAT2_RAD = SLAT2 * DEG_RAD
        private const val OLON_RAD = OLON * DEG_RAD
        private const val OLAT_RAD = OLAT * DEG_RAD

        fun convertGpsToGrid(lat: Double, lng: Double): MapGrid {
            var sn =
                tan(Math.PI * 0.25 + SLAT2_RAD * 0.5) / tan(Math.PI * 0.25 + SLAT1_RAD * 0.5)
            sn = ln(cos(SLAT1_RAD) / cos(SLAT2_RAD)) / ln(sn)
            var sf = tan(Math.PI * 0.25 + SLAT1_RAD * 0.5)
            sf = sf.pow(sn) * cos(SLAT1_RAD) / sn
            var ro = tan(Math.PI * 0.25 + OLAT_RAD * 0.5)
            ro = RE_GRID * sf / ro.pow(sn)

            var ra = tan(Math.PI * 0.25 + (lat) * DEG_RAD * 0.5)
            ra = RE_GRID * sf / ra.pow(sn)
            var theta: Double = lng * DEG_RAD - OLON_RAD
            if (theta > Math.PI) theta -= 2.0 * Math.PI
            if (theta < -Math.PI) theta += 2.0 * Math.PI
            theta *= sn
            return MapGrid(
                floor(ra * sin(theta) + XO + 0.5).toInt(),
                floor(ro - ra * cos(theta) + YO + 0.5).toInt()
            )
        }
    }
}