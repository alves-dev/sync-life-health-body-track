package synclife.health.bodytrack.infrastructure.broker

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import synclife.health.bodytrack.event.v3.EventOrchestratorSubscriptionRequestedV1

@Component
class PublishStart(
    private val sender: RabbitMqSender
) {

    @Value("\${sync-life.health.body-track.queue.v3}")
    private lateinit var bodyTrackQueueV3: String

    @PostConstruct
    fun init(){
        val subscriptionRequestedV1: EventOrchestratorSubscriptionRequestedV1 =
            EventOrchestratorSubscriptionRequestedV1(bodyTrackQueueV3)
        sender.publishEventV3(subscriptionRequestedV1)
    }
}