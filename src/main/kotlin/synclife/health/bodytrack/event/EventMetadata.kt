package synclife.health.bodytrack.event

import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDateTime

class EventMetadata() {

    lateinit var origin: String

    @JsonAlias("created_at")
    lateinit var createdAt: LocalDateTime

    override fun toString(): String {
        return "EventMetadata(origin='$origin', createdAt=$createdAt)"
    }
}