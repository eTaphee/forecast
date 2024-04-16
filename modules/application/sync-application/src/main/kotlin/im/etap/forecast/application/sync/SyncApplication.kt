package im.etap.forecast.application.sync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication(scanBasePackages = ["im.etap.forecast"])
class SyncApplication

fun main(args: Array<String>) {
    runApplication<SyncApplication>(*args)
}
