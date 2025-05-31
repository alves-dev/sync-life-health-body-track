package synclife.health.bodytrack.domain.measure

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import synclife.health.bodytrack.domain.BaseEntity
import java.time.ZonedDateTime

@Entity
@Table(name = "measurement")
class Measurement(

    @Column(name = "measure", nullable = false, updatable = false)
    var measure: String,

    @Column(name = "value", nullable = false)
    var value: Double,

    // BaseEntity
    personId: String,
    datetime: ZonedDateTime

) : BaseEntity() {
    init {
        this.personId = personId
        this.datetime = datetime
    }
}