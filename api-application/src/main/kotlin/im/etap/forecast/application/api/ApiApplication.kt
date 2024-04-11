package im.etap.forecast.application.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ForecastApplication

fun main(args: Array<String>) {
    runApplication<ForecastApplication>(*args)
}
