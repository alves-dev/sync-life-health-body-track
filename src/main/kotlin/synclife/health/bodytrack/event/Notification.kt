package synclife.health.bodytrack.event

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @see <a href="https://github.com/alves-dev/SyncLife/blob/main/events.md#event-notificationv1">event-notification.v1</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Notification(

    @JsonProperty("recipient_type")
    val recipientType: RecipientType,

    @JsonProperty("recipient_id")
    val recipientId: String,

    val level: Level = Level.MEDIUM,
    val type: Type,
    val title: String,
    val message: String,
    val id: String,
    val private: Boolean = false,

    @JsonProperty("option_1")
    val option1: String? = null,

    @JsonProperty("option_2")
    val option2: String? = null,

    @JsonProperty("option_3")
    val option3: String? = null,

    @JsonProperty("show_after")
    val showAfter: String? = null,

    @JsonProperty("show_before")
    val showBefore: String? = null,

    val origin: String = "Body Track"
) {
    override fun toString(): String {
        return "Notification(title='$title', message=$message)"
    }

    companion object {
        fun createDefaultNotification(
            title: String,
            message: String,
            id: String = UUID.randomUUID().toString(),
            level: Level = Level.MEDIUM,
            minutesAddBefore: Long = 120
        ): Notification {
            return Notification(
                recipientType = RecipientType.PERSON,
                recipientId = "igor_alves",
                level = level,
                type = Type.NOTIFICATION,
                title = title,
                message = message,
                id = id,
                showBefore = getDatetimeString(minutesAddBefore)
            )
        }

        private fun getDatetimeString(minutes: Long): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val dateTime = LocalDateTime.now().plusMinutes(minutes)
            return dateTime.format(formatter)
        }
    }

    enum class RecipientType() {
        PERSON
    }

    enum class Level() {
        LOW, MEDIUM, HIGH
    }

    enum class Type() {
        DEBUG, NOTIFICATION, REMINDER, QUESTION_2, QUESTION_3
    }
}
