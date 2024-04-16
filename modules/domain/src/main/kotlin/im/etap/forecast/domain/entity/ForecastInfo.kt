package im.etap.forecast.domain.entity

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import java.time.LocalDate
import java.time.LocalTime


@Entity
class ForecastInfo(
    val category: String,
    val forecastDate: LocalDate,
    val forecastTime: LocalTime,
    val forecastValue: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forecast_sync_id")
    val forecastSync: ForecastSync
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0
}
