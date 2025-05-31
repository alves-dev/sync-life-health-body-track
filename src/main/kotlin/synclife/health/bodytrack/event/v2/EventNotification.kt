package synclife.health.bodytrack.event.v2

import java.time.LocalDateTime

/**
 * @see <a href="https://github.com/alves-dev/SyncLife/blob/main/events.md#event-notificationv1">event-notification.v1</a>
 */
class EventNotification(
    var notification: Notification
) : EventBaseV2(EventTypeV2.NOTIFICATION_V1) {
    init {
        this.personId = notification.recipientId
        this.datetime = LocalDateTime.now()
        this.metadata = EventMetadata.createForSendEvent()
    }

    override fun toString(): String {
        return "EventNotification(notification=$notification)"
    }

}