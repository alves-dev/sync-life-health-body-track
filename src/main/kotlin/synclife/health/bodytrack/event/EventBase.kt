package synclife.health.bodytrack.event

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

/**
 * @see <a href="https://github.com/alves-dev/SyncLife/blob/main/events.md#todos-os-eventos-v%C3%A3o-ter-os-seguintes-campos">event-base</a>
 */
open class EventBase(@JsonIgnore val type: EventType) {

    @JsonAlias("person_id")
    @JsonProperty("person_id")
    lateinit var personId: String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    lateinit var datetime: LocalDateTime

    @JsonAlias("meta_data")
    @JsonProperty("meta_data")
    lateinit var metadata: EventMetadata

    override fun toString(): String {
        return "EventBase(personId='$personId', datetime=$datetime, metadata=$metadata)"
    }
}