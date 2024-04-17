package im.etap.forecast.application.core.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class ErrorCode(val status: HttpStatus, val description: String) {
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "내부 오류 발생"),
    VALIDATION_ERROR(BAD_REQUEST, "유효성 검사 오류 발생"),
    FORECAST_SYNC_NOT_FOUND(NOT_FOUND, "기상예보 동기화 정보를 찾을 수 없음"),
    FORECAST_SYNC_API_ERROR(INTERNAL_SERVER_ERROR, "기상예보 동기화 API 서버 오류 발생"),
}