package synclife.health.bodytrack.domain.sleep

import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import synclife.health.bodytrack.event.v2.EventNotification
import synclife.health.bodytrack.event.v2.EventSleep
import synclife.health.bodytrack.event.v2.Notification
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

    @Transactional
    fun processSleepSummary() {
        val personIdList: List<String> = sleepRepository.findDistinctPersonId()

        for (personId in personIdList) {
            val wakeUp: SleepTracking? = sleepRepository.findLastWakeUpByPersonId(personId)
            if (wakeUp == null) continue

            val sleep: SleepTracking? = sleepRepository.findLastSleepByPersonIdAndDatetime(personId, wakeUp.datetime)
            if (sleep == null) continue

            val notificationId: String = "$personId-${sleep.id}"
            val notificationTitle: String = "Sleep: $personId"

            if (sleep.computed) {
                val message = "${sleep.id}: Sleep já computado"
                log.warn(message)
                fireNotification(notificationTitle, message, notificationId)
                continue
            }

            val result: OperationResult = SleepTracking.markAsComputed(sleep, wakeUp)
            if (result.status) {
                val date = wakeUp.datetime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

                val duration = Duration.ofMinutes(result.reason.toLong())
                val hours = duration.toHours()
                val minutes = duration.toMinutesPart()
                fireDebugNotification(
                    notificationTitle,
                    "Dormiu: ${result.reason} minutos em $date, o que da $hours horas e $minutes minutos"
                )
            } else {
                log.warn(result.reason)
                fireNotification(notificationTitle, result.reason, notificationId)
            }
        }
    }

    private fun fireNotification(title: String, message: String, id: String) {
        val notification = Notification.Companion.createDefaultNotification(title, message, id)
        val event = EventNotification(notification)
        rabbitMqSender.publishEventV2Notification(event)
    }

    private fun fireDebugNotification(title: String, message: String) {
        val notification = Notification.Companion.createDefaultDebug(title, message)
        val event = EventNotification(notification)
        rabbitMqSender.publishEventV2Notification(event)
    }
}