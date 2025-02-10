package synclife.health.bodytrack.event

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

/**
 * @see <a href="https://github.com/alves-dev/SyncLife/blob/main/events.md#todos-os-eventos-v%C3%A3o-ter-os-seguintes-campos">event-base</a>
 */
class EventMetadata() {

    lateinit var origin: String

    @JsonAlias("created_at")
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    lateinit var createdAt: LocalDateTime

    override fun toString(): String {
        return "EventMetadata(origin='$origin', createdAt=$createdAt)"
    }

    companion object{
        fun createForSendEvent(): EventMetadata {
            val meta = EventMetadata()
            meta.origin = "Body Track"
            meta.createdAt = LocalDateTime.now()
            return meta
        }
    }
}