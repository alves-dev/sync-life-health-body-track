package synclife.health.bodytrack.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import synclife.health.bodytrack.event.EventSync

@Service
class LogEvents {

    private val log: Logger = LoggerFactory.getLogger(Service::class.java)

    @Async
    @EventListener
    fun processEventBase(event: EventSync) {
        log.info(event.toString())
        log.info("{}: {}", event.getEventFlow(), event)
    }
}