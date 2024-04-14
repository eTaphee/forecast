package im.etap.forecast.core.map

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MapUtilTest {

    @Test
    @DisplayName("위도 경도를 그리드로 변환")
    fun convertGpsToGrid() {
        val data = listOf(
            Sample("의정부1동", 37.7437027777777, 127.049844444444, 61, 131),
            Sample("의정부2동", 37.7380055555555, 127.044933333333, 61, 130),
            Sample("호원1동", 37.7091944444444, 127.050822222222, 61, 130),
            Sample("호원2동", 37.7237444444444, 127.045197222222, 61, 130),
            Sample("장암동", 37.7235972222222, 127.056344444444, 61, 130)
        )

        data.forEach {
            val grid = MapUtil.convertGpsToGrid(it.lat, it.lng)
            assertEquals(it.x, grid.x)
            assertEquals(it.y, grid.y)
        }
    }

    data class Sample(
        val name: String,
        val lat: Double,
        val lng: Double,
        val x: Int,
        val y: Int
    )
}