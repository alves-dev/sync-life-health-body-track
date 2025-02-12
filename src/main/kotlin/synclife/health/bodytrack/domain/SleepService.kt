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
import synclife.health.bodytrack.event.EventNotification
import synclife.health.bodytrack.event.EventSleep
import synclife.health.bodytrack.event.Notification
import synclife.health.bodytrack.infrastructure.broker.RabbitMqSender
import synclife.health.bodytrack.infrastructure.repository.SleepTrackingRepository
import synclife.health.bodytrack.utils.OperationResult
import java.time.Duration
import java.time.format.DateTimeFormatter

@Service
class SleepService {

    private val log: Logger = LoggerFactory.getLogger(Service::class.java)

    @Autowired
    private lateinit var sleepRepository: SleepTrackingRepository

    @Autowired
    private lateinit var rabbitMqSender: RabbitMqSender

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
        val personIdList: List<String> = sleepRepository.findDistinctPersonId()

        for (personId in personIdList) {
            val wakeUp: SleepTracking? = sleepRepository.findLastWakeUpByPersonId(personId)
            if (wakeUp == null) continue

            val sleep: SleepTracking? = sleepRepository.findLastSleepByPersonIdAndDatetime(personId, wakeUp.datetime)
            if (sleep == null) continue

            if (sleep.computed) {
                val message = "${sleep.id}: Sleep já computado"
                log.warn(message)
                fireNotification("Sleep: $personId", message)
                continue
            }

            val result: OperationResult = SleepTracking.markAsComputed(sleep, wakeUp)
            if (result.status) {
                val date = wakeUp.datetime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

                val duration = Duration.ofMinutes(result.reason.toLong())
                val hours = duration.toHours()
                val minutes = duration.toMinutesPart()
                fireDebugNotification("Sleep: $personId", "Dormiu: ${result.reason} minutos em $date, o que da $hours horas e $minutes minutos")
            }else{
                log.warn(result.reason)
                fireNotification("Sleep: $personId", result.reason)
            }
        }
    }

    private fun fireNotification(title: String, message: String) {
        val notification = Notification.createDefaultNotification(title, message)
        val event = EventNotification(notification)
        rabbitMqSender.sendNotification(event)
    }

    private fun fireDebugNotification(title: String, message: String) {
        val notification = Notification.createDefaultDebug(title, message)
        val event = EventNotification(notification)
        rabbitMqSender.sendNotification(event)
    }
}
