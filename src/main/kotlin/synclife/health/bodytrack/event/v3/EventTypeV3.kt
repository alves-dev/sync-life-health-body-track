package synclife.health.bodytrack.event.v3

import synclife.health.bodytrack.event.EventFlow
import synclife.health.bodytrack.event.EventType
import java.util.*
import kotlin.reflect.KClass

enum class EventTypeV3(val eventText: String, val flow: EventFlow, val eventBase: KClass<out EventBaseV3>) : EventType {
    ORCHESTRATOR_SUBSCRIPTIONS_REQUESTED_V1(
        "orchestrator.subscriptions.requested.v1",
        EventFlow.PRODUCED,
        EventOrchestratorSubscriptionRequestedV1::class
    ), // https://github.com/alves-dev/sync-life/blob/main/events/model/orchestrator/subscriptions.requested.v1.json
    HEALTH_BODY_MEASUREMENT_V1(
        "health.body.measurement.v1",
        EventFlow.CONSUMED,
        EventHealthBodyMeasurementV1::class
    ); // https://github.com/alves-dev/sync-life/blob/main/events/model/health/body.measurement.v1.json

    override fun getEventFlow(): EventFlow = flow

    companion object {
        fun getEventClass(eventText: String): KClass<out EventBaseV3> {
            return Arrays.stream(entries.toTypedArray())
                .filter { e -> e.eventText == eventText }.findFirst().get().eventBase
        }
    }
}