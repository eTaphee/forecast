package im.etap.forecast.exception

/**
 * 유효성 검사 예외
 *
 * @param field 필드
 * @param message 메시지
 */
data class ValidationError(
    val field: String,
    val message: String?
)
