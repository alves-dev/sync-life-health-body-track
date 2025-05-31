package synclife.health.bodytrack.event.v3

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import synclife.health.bodytrack.event.EventFlow
import synclife.health.bodytrack.event.EventSync
import java.net.URI
import java.net.URL
import java.time.ZonedDateTime
import java.util.*

/**
 * @see <a href="https://github.com/alves-dev/sync-life/blob/main/events/events_v3.md">event-base</a>
 */
open class EventBaseV3(@JsonIgnore val type: EventTypeV3) : EventSync {

    val URI: String = "/services/body-track"

    @JsonProperty("specversion")
    private var specVersion: String? = null

    @JsonProperty("source")
    private var source: URI? = null

    @JsonProperty("id")
    private var id: UUID? = null

    @JsonProperty("time")
    private var time: ZonedDateTime? = null

    @JsonProperty("datacontenttype")
    private var dataContentType: String? = null

    @JsonProperty("dataschema")
    private var datasChema: URL? = null

    @JsonProperty("extensions")
    private var extensions: MutableMap<String?, Any?>? = null

    override fun toString(): String {
        return "EventBaseV3{" +
                "specVersion='" + specVersion + '\'' +
                ", type=" + type +
                ", source=" + source +
                ", id=" + id +
                ", time=" + time +
                ", dataContentType='" + dataContentType + '\'' +
                ", datasChema=" + datasChema +
                ", extensions=" + extensions +
                '}'
    }

    override fun getEventFlow(): EventFlow {
        return type.getEventFlow()
    }
}