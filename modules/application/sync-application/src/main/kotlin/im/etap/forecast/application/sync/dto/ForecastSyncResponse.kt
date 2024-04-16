package im.etap.forecast.application.sync.dto

import im.etap.forecast.domain.entity.ForecastSync

/**
 * 기상예보 동기화 응답
 *
 * @param id Sync ID
 * @param isFinished 동기화 완료 여부
 */
data class ForecastSyncResponse(val id: Long, val isFinished: Boolean) {
    companion object {
        fun from(entity: ForecastSync): ForecastSyncResponse {
            return ForecastSyncResponse(entity.id, entity.isFinished)
        }
    }
}
