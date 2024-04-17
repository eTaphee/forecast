package im.etap.forecast.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import im.etap.forecast.exception.ErrorCode.INTERNAL_ERROR

data class ErrorResponse(
    val code: ErrorCode,
    @JsonInclude(NON_NULL)
    val message: String?,
    val description: String,
    @JsonInclude(NON_NULL)
    val errors: List<ValidationError>? = null
) {
    constructor(code: ErrorCode, message: String?) : this(code, message, code.description)

    constructor(code: ErrorCode, errors: List<ValidationError>?) : this(
        code,
        null,
        code.description,
        errors
    )

    fun toException(): ForecastException {
        return ForecastException(code, message, errors)
    }
}

fun Exception.toResponse(): ErrorResponse {
    return ErrorResponse(INTERNAL_ERROR, message)
}