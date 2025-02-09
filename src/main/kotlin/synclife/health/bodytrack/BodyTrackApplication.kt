package synclife.health.bodytrack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class BodyTrackApplication

fun main(args: Array<String>) {
	runApplication<BodyTrackApplication>(*args)
}
