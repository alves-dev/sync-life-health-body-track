package synclife.health.bodytrack.event.v3

import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URI

class EventOrchestratorSubscriptionRequestedV1(queueName: String) :
    EventBaseV3(EventTypeV3.ORCHESTRATOR_SUBSCRIPTIONS_REQUESTED_V1) {

    private val URL: String =
        "https://raw.githubusercontent.com/alves-dev/sync-life/main/events/schema/orchestrator/subscriptions.requested.v1.json"
    private val SERVICE_ID: String = "body-track"

    @JsonProperty("data")
    var data: Data

    init {
        setValuesBase(URI(URL).toURL(), getExtensions())
        data = Data(
            SERVICE_ID,
            queueName,
            Subscriptions(listOf(EventTypeV3.HEALTH_BODY_MEASUREMENT_V1.eventText) as MutableList<String>)
        )
    }

    data class Data(
        @JsonProperty("service_id") val serviceId: String,
        @JsonProperty("queue_name") val queueName: String,
        @JsonProperty("subscriptions") val subscriptions: Subscriptions
    )

    data class Subscriptions(
        @JsonProperty("event_types") val eventTypes: MutableList<String>
    )

    private fun getExtensions(): MutableMap<String, Any> {
        return mutableMapOf(
            "causation_id" to "event-startup",
            "origin" to SERVICE_ID
        )
    }
}