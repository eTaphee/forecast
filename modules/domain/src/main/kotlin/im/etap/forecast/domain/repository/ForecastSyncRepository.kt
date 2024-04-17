package im.etap.forecast.domain.repository

import im.etap.forecast.domain.entity.ForecastSync
import org.locationtech.jts.geom.Point
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
internal interface ForecastSyncRepository : JpaRepository<ForecastSync, Long> {

    @Query("SELECT f FROM ForecastSync f WHERE f.baseDateTime = :baseDateTime AND f.location = :location AND f.isFailed = false")
    fun findByBaseDateTimeAndLocation(
        baseDateTime: LocalDateTime,
        location: Point
    ): Optional<ForecastSync>
}