package im.etap.forecast.exception

enum class ErrorCode(val status: Int, val description: String) {
    VALIDATION_ERROR(400, "유효성 검사 오류 발생"),

    FORECAST_SYNC_NOT_FOUND(404, "기상예보 동기화 정보를 찾을 수 없음"),

    INTERNAL_ERROR(500, "내부 오류 발생"),
    FORECAST_SYNC_API_ERROR(500, "기상예보 동기화 API 서버 오류 발생"),
}