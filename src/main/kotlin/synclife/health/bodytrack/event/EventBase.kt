package synclife.health.bodytrack.event

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

// https://github.com/alves-dev/SyncLife/blob/main/events.md#todos-os-eventos-v%C3%A3o-ter-os-seguintes-campos
open class EventBase(@JsonIgnore val type: EventType) {

    @JsonAlias("person_id")
    lateinit var personId: String

    lateinit var datetime: LocalDateTime

    @JsonAlias("meta_data")
    lateinit var metadata: EventMetadata

    override fun toString(): String {
        return "EventBase(personId='$personId', datetime=$datetime, metadata=$metadata)"
    }
}