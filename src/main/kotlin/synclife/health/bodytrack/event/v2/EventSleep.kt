package synclife.health.bodytrack.event.v2

import synclife.health.bodytrack.domain.sleep.SleepAction

/**
 * @see <a href="https://github.com/alves-dev/SyncLife/blob/main/events.md#event-healthbody_tracksleepv1">event-sleep</a>
 */
class EventSleep() : EventBaseV2(EventTypeV2.HEALTH_BODY_TRACK_SLEEP_V1) {
    lateinit var action: SleepAction

    override fun toString(): String {
        return "EventSleep(action=$action, super=${super.toString()})"
    }
}