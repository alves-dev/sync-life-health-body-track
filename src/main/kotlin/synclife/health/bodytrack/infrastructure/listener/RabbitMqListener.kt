package synclife.health.bodytrack.infrastructure.listener

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import synclife.health.bodytrack.event.EventBase
import synclife.health.bodytrack.event.EventType
import kotlin.reflect.KClass

@Service
class RabbitMqListener {

    private val log: Logger = LoggerFactory.getLogger(RabbitMqListener::class.java)

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    private val jsonMapper: JsonMapper = JsonMapper.builder().findAndAddModules()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()

    @RabbitListener(queues = ["\${sync-life.health.body-track.queue}"])
    fun handleEvent(message: String?) {
        try {
            val node = jsonMapper.readTree(message)
            val eventTypeValue: String = node.get("type").asText()

            val eventClass: KClass<out EventBase> = EventType.getEventClass(eventTypeValue)
            val event: EventBase = jsonMapper.readValue(message, eventClass.java)

            eventPublisher.publishEvent(event)
        } catch (e: Exception) {
            log.warn("[RabbitMqListener] Error parsing: $message")
            log.warn(e.toString())
        }
    }
}