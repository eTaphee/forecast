package im.etap.forecast.core.time

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeUtil {
    companion object {
        private val TIME_PATTERN = DateTimeFormatter.ofPattern("HHmm")

        /**
         * 시작 시간부터 interval 시간 간격으로 LocalTime 리스트를 생성한다.
         *
         * @param startTime 시작 시간(HHmm)
         * @param interval 시간 간격(양수)
         */
        fun generateTimeIntervals(startTime: String, interval: Long): List<LocalTime> {
            if (interval <= 0) throw IllegalArgumentException("interval must be greater than 0")

            val list = ArrayList<LocalTime>()
            var prev = LocalTime.MIDNIGHT
            var time = LocalTime.parse(startTime, TIME_PATTERN)
            while (time.isAfter(prev)) {
                list.add(time)
                prev = time
                time = time.plusHours(interval)
            }
            return list.toList()
        }

        /**
         * 시간 간격에서 기준 시간에 대한 참조 시간을 반환한다.
         *
         * @param times 시간 리스트
         * @param dateTime 기준 시간(기본값: 현재 시간)
         * @return 기준 시간의 참조 시간
         */
        fun getReferenceDateTime(
            times: List<LocalTime>, dateTime: LocalDateTime = LocalDateTime.now()
        ): LocalDateTime {
            var time = times.reversed().firstOrNull { dateTime.toLocalTime().isAfter(it) }
            var date = dateTime.toLocalDate()
            if (time == null) {
                date = date.minusDays(1)
                time = times.last()
            }
            return LocalDateTime.of(date, time)
        }
    }
}