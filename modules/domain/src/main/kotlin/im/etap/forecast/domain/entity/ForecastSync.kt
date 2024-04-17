package im.etap.forecast.domain.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY
import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

// TODO: spatial index + datetime index
@Entity
internal class ForecastSync(
    val baseDateTime: LocalDateTime,
    val location: Point
) : AuditableEntity() {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0

    private var finishedAt: LocalDateTime? = null

    var isFailed: Boolean = false
        private set

    val isFinished: Boolean
        get() = finishedAt != null

    @OneToMany(
        fetch = LAZY,
        mappedBy = "forecastSync",
        cascade = [ALL],
        orphanRemoval = true
    )
    private val _forecastInfos: MutableList<ForecastInfo> = mutableListOf()

    val forecastInfos: List<ForecastInfo>
        get() = _forecastInfos.toList()

    fun addForecastInfos(forecastInfos: List<ForecastInfo>) {
        _forecastInfos.addAll(forecastInfos)
        finishedAt = LocalDateTime.now()
    }

    fun fail() {
        isFailed = true
        finishedAt = LocalDateTime.now()
    }
}