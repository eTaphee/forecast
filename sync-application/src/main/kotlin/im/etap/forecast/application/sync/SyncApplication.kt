package im.etap.forecast.application.sync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SyncApplication

fun main(args: Array<String>) {
    runApplication<SyncApplication>(*args)
}
