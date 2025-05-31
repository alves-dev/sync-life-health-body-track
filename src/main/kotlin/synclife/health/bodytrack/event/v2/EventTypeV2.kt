package synclife.health.bodytrack.event.v2

import synclife.health.bodytrack.event.EventFlow
import synclife.health.bodytrack.event.EventType
import java.util.*
import kotlin.reflect.KClass

enum class EventTypeV2(val eventText: String, val flow: EventFlow, val eventBase: KClass<out EventBaseV2>) : EventType {
    HEALTH_BODY_TRACK_SLEEP_V1(
        "HEALTH.BODY_TRACK.SLEEP.V1",
        EventFlow.CONSUMED,
        EventSleep::class
    ),// https://github.com/alves-dev/sync-life/blob/main/events/events_v2.md#event-healthbody_tracksleepv1
    NOTIFICATION_V1(
        "NOTIFICATION.V1",
        EventFlow.PRODUCED,
        EventNotification::class
    ); // https://github.com/alves-dev/SyncLife/blob/main/events.md#event-notificationv1

    override fun getEventFlow(): EventFlow = flow

    companion object {
        fun getEventClass(eventText: String): KClass<out EventBaseV2> {
            return Arrays.stream(EventTypeV2.entries.toTypedArray())
                .filter { e -> e.eventText == eventText }.findFirst().get().eventBase
        }
    }
}