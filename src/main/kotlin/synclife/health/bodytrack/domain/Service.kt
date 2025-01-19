package synclife.health.bodytrack.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import synclife.health.bodytrack.domain.sleep.SleepTracking
import synclife.health.bodytrack.event.EventBase
import synclife.health.bodytrack.event.EventSleep
import synclife.health.bodytrack.infrastructure.repository.SleepTrackingRepository

@Service
class Service {

    private val log: Logger = LoggerFactory.getLogger(Service::class.java)

    @Autowired
    private lateinit var sleepRepository: SleepTrackingRepository

    @Async
    @EventListener
    fun processEventSleep(event: EventSleep) {
        val e = SleepTracking(event.action, event.personId, event.datetime)
        sleepRepository.save(e)
    }

    @Async
    @EventListener
    fun processEventBase(event: EventBase) {
        log.info(event.toString())
    }
}
