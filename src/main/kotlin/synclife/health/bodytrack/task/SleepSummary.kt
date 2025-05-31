package synclife.health.bodytrack.task

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import synclife.health.bodytrack.domain.sleep.SleepService

@Component
class SleepSummary {

    private val log: Logger = LoggerFactory.getLogger(SleepSummary::class.java)

    @Autowired
    private lateinit var sleepTrackingService: SleepService

    @Scheduled(cron = "@hourly")
    fun task() {
        log.info("SleepSummary: start")
        sleepTrackingService.processSleepSummary()
        log.info("SleepSummary: end")
    }
}