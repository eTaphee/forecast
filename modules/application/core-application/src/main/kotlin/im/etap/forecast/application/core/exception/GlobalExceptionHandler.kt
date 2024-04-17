package im.etap.forecast.application.core.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import im.etap.forecast.application.core.exception.ErrorCode.INTERNAL_ERROR
import im.etap.forecast.application.core.exception.ErrorCode.VALIDATION_ERROR
import mu.KotlinLogging
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ForecastException::class)
    fun handleForecastException(e: ForecastException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "$e is occurred" }
        return ResponseEntity
            .status(e.code.status)
            .body(e.toResponse())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "$e is occurred" }
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(
                ErrorResponse(VALIDATION_ERROR, e.fieldErrors.map {
                    ValidationError(
                        it.field, it.defaultMessage
                    )
                })
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e) { "$e is occurred" }
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(e.toResponse())
    }
}

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

fun Exception.toResponse(): ErrorResponse {
    return ErrorResponse(INTERNAL_ERROR, message)
}

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

data class ValidationError(
    val field: String,
    val message: String?
)