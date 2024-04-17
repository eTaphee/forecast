package im.etap.forecast.core.time

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TimeUtilTest {
    @Test
    @DisplayName("시간 간격 생성")
    fun generateTimeIntervals() {
        val startTime = "0200"
        val intervalHour = 3L
        val result = TimeUtil.generateTimeIntervals(startTime, intervalHour)

        assertEquals(8, result.size)
        assertEquals("02:00", result[0].toString())
        assertEquals("05:00", result[1].toString())
        assertEquals("08:00", result[2].toString())
        assertEquals("11:00", result[3].toString())
        assertEquals("14:00", result[4].toString())
        assertEquals("17:00", result[5].toString())
        assertEquals("20:00", result[6].toString())
        assertEquals("23:00", result[7].toString())
    }

    @Test
    @DisplayName("참조 시간 반환")
    fun getReferenceDateTime() {
        val times = TimeUtil.generateTimeIntervals("0200", 3)

        val ldt1 = LocalDateTime.parse("2024-04-14T01:59:00")
        val result1 = TimeUtil.getReferenceDateTime(times, ldt1)
        assertEquals("2024-04-13T23:00", result1.toString())

        val ldt2 = LocalDateTime.parse("2024-04-14T16:51:00")
        val result2 = TimeUtil.getReferenceDateTime(times, ldt2)
        assertEquals("2024-04-14T14:00", result2.toString())

        val ldt3 = LocalDateTime.parse("2024-04-14T23:51:00")
        val result3 = TimeUtil.getReferenceDateTime(times, ldt3)
        assertEquals("2024-04-14T23:00", result3.toString())
        val ldt4 = LocalDateTime.parse("2024-04-14T02:10:00")
        val result4 = TimeUtil.getReferenceDateTime(times, ldt4.minusMinutes(10))
        assertEquals("2024-04-13T23:00", result4.toString())
    }
}