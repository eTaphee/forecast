package im.etap.forecast

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class SyncApplication

fun main(args: Array<String>) {
    runApplication<SyncApplication>(*args)
}
