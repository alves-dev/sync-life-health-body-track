package synclife.health.bodytrack.event.v3

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

/**
 * @see <a href="https://github.com/alves-dev/sync-life/blob/main/events/model/health/body.measurement.v1.json">event-sleep</a>
 */
class EventHealthBodyMeasurementV1() : EventBaseV3(EventTypeV3.HEALTH_BODY_MEASUREMENT_V1) {

    @JsonProperty("data")
    lateinit var data: Data

    //@JvmRecord
    data class Data(
        @JsonProperty("person_id") val personId: String,
        @JsonProperty("datetime") val datetime: ZonedDateTime,
        @JsonProperty("measure") val measure: String,
        @JsonProperty("value") val value: Double
    )

    override fun toString(): String {
        return "EventHealthBodyMeasurementV1(data=$data, super=${super.toString()})"
    }
}