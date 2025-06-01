package synclife.health.bodytrack.infrastructure.broker

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import synclife.health.bodytrack.event.v2.EventBaseV2
import synclife.health.bodytrack.event.v2.EventTypeV2
import synclife.health.bodytrack.event.v3.EventBaseV3
import synclife.health.bodytrack.event.v3.EventTypeV3
import kotlin.reflect.KClass

@Service
@DependsOn("logEvents")
class RabbitMqListener(
    private val eventPublisher: ApplicationEventPublisher
) {

    private val log: Logger = LoggerFactory.getLogger(RabbitMqListener::class.java)

    private val jsonMapper: JsonMapper = JsonMapper.builder().findAndAddModules()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()

    @RabbitListener(queues = ["\${sync-life.health.body-track.queue}"])
    fun handleEvent(message: String) {
        try {
            val node = jsonMapper.readTree(message)
            val eventTypeValue: String = node.get("type").asText()

            val eventClass: KClass<out EventBaseV2> = EventTypeV2.getEventClass(eventTypeValue)
            val messageReplaced = message.replace(eventTypeValue, eventTypeValue.replace(".", "_"))
            val event: EventBaseV2 = jsonMapper.readValue(messageReplaced, eventClass.java)

            eventPublisher.publishEvent(event)
        } catch (e: Exception) {
            log.warn("[RabbitMqListener] V2 Error parsing: $message")
            log.warn(e.toString())
        }
    }

    @RabbitListener(queues = ["\${sync-life.health.body-track.queue.v3}"])
    fun handleEventV3(message: String) {
        try {
            val node = jsonMapper.readTree(message)
            val eventTypeValue: String = node.get("type").asText()

            val eventClass: KClass<out EventBaseV3> = EventTypeV3.getEventClass(eventTypeValue)
            val event: EventBaseV3 = jsonMapper.readValue(message, eventClass.java)

            eventPublisher.publishEvent(event)
        } catch (e: Exception) {
            log.warn("[RabbitMqListener] V3 Error parsing: $message")
            log.warn(e.toString())
        }
    }
}