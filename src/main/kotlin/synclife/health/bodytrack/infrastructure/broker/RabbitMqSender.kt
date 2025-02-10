package synclife.health.bodytrack.infrastructure.broker

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import synclife.health.bodytrack.event.EventBase
import synclife.health.bodytrack.event.EventNotification


@Service
class RabbitMqSender(
    private val rabbitTemplate: RabbitTemplate
) {
    private val log: Logger = LoggerFactory.getLogger(RabbitMqSender::class.java)

    val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        registerKotlinModule() // Adiciona suporte total ao Kotlin
        registerModule(JavaTimeModule()) // Suporte ao LocalDateTime e outros tipos de data/hora do Java 8+
    }

    private val notificationRoutingKey = "notification"

    @Value("\${sync-life.rabbitmq.exchange}")
    private lateinit var exchange: String

    fun sendNotification(notification: EventNotification) {
        sendMessage(notification, notificationRoutingKey)
    }

    private fun sendMessage(event: EventBase, routingKey: String) {
        try {
            val message = objectMapper.writeValueAsString(event)
            rabbitTemplate.convertAndSend(exchange, routingKey, message)
            log.info("[RabbitMqSender] Sent message: $message")
        } catch (e: Exception) {
            log.error("[RabbitMqSender] Error sending message: ${e.message}", e)
        }
    }
}