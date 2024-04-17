package im.etap.forecast.exception

import im.etap.forecast.exception.ErrorCode.VALIDATION_ERROR
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