package synclife.health.bodytrack.task

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import synclife.health.bodytrack.domain.Service

@Component
class SleepSummary {

    @Autowired
    private lateinit var sleepTrackingService: Service

    @Scheduled(cron = "* * */1 * * *")
    fun task() {
        sleepTrackingService.processSleepSummary();
    }
}