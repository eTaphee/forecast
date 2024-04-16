package im.etap.forecast.application.core.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND

enum class ErrorCode(val status: HttpStatus, val message: String) {
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "내부 오류 발생"),
    FORECAST_SYNC_NOT_FOUND(NOT_FOUND, "기상예보 동기화 정보를 찾을 수 없음")
}