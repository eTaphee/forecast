package im.etap.forecast.exception

class ForecastException(
    val code: ErrorCode,
    message: String?,
    val errors: List<ValidationError>? = null
) : RuntimeException(message) {
    constructor(code: ErrorCode) : this(code, code.description)

    fun toResponse(): ErrorResponse {
        return ErrorResponse(code, message, code.description, errors)
    }
}