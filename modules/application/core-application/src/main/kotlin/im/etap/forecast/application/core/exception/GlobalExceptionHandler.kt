package im.etap.forecast.application.core.exception

import im.etap.forecast.application.core.exception.ErrorCode.INTERNAL_ERROR
import mu.KotlinLogging
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e) { "$e is occurred" }
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(e.toResponse())
    }
}

class ForecastException(
    val code: ErrorCode
) : RuntimeException(code.message) {
    fun toResponse(): ErrorResponse {
        return ErrorResponse(code)
    }
}

fun Exception.toResponse(): ErrorResponse {
    return ErrorResponse(INTERNAL_ERROR, message)
}

data class ErrorResponse(
    val code: ErrorCode,
    val message: String?
) {
    constructor(code: ErrorCode) : this(code, code.message)
}