package synclife.health.bodytrack.domain

import jakarta.transaction.Transactional
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
import synclife.health.bodytrack.utils.OperationResult

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

    @Transactional
    fun processSleepSummary() {
        val listNames: List<String> = sleepRepository.findDistinctPersonId()

        for (name in listNames) {
            val wakeUp :SleepTracking? = sleepRepository.findLastWakeUpByPersonId(name)
            if (wakeUp == null) continue

            val sleep :SleepTracking? = sleepRepository.findLastSleepByPersonIdAndDatetime(name, wakeUp.datetime)
            if (sleep == null) continue

            if (sleep.computed){
                println("sleep já computado")
                //TODO: lança uma notification
                continue
            }

            val result : OperationResult = SleepTracking.markAsComputed(sleep, wakeUp)
            if (!result.status){
                println(result.reason)
                //TODO: lança uma notification
            }
        }
    }
}
