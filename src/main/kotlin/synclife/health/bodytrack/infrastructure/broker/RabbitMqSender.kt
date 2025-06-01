package synclife.health.bodytrack.infrastructure.broker

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import synclife.health.bodytrack.event.EventSync
import synclife.health.bodytrack.event.v2.EventBaseV2
import synclife.health.bodytrack.event.v2.EventNotification
import synclife.health.bodytrack.event.v3.EventBaseV3


@Service
@DependsOn("logEvents")
class RabbitMqSender(
    private val rabbitTemplate: RabbitTemplate,
    private val eventPublisher: ApplicationEventPublisher

) {
    private val log: Logger = LoggerFactory.getLogger(RabbitMqSender::class.java)

    val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        registerKotlinModule() // Adiciona suporte total ao Kotlin
        registerModule(JavaTimeModule()) // Suporte ao LocalDateTime e outros tipos de data/hora do Java 8+
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    private val notificationRoutingKey = "notification"

    @Value("\${sync-life.rabbitmq.exchange}")
    private lateinit var exchangeV2: String

    @Value("\${sync-life.rabbitmq.exchange.v3}")
    private lateinit var exchangeV3: String


    fun publishEventV2Notification(notification: EventNotification) {
        this.publishEvent(notification, exchangeV2, notificationRoutingKey)
    }

    fun <T : EventBaseV2> publishEventV2(event: T) {
        this.publishEvent(event, exchangeV2, event.type.eventText)
    }

    fun <T : EventBaseV3> publishEventV3(event: T) {
        this.publishEvent(event, exchangeV3, event.type.eventText)
    }

    private fun <T : EventSync> publishEvent(event: T, exchange: String, routingKey: String) {
        try {
            val message = objectMapper.writeValueAsString(event)
            rabbitTemplate.convertAndSend(exchange, routingKey, message)
            log.info("[RabbitMqSender] Sent message: $message")
            eventPublisher.publishEvent(event) // TODO: não ta funcionando
        } catch (e: java.lang.Exception) {
            log.error("[RabbitMqSender] Error sending message: ${e.message}", e)
        }
    }
}