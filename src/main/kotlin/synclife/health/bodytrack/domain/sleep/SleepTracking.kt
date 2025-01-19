package synclife.health.bodytrack.domain.sleep

import jakarta.persistence.*
import synclife.health.bodytrack.domain.BaseEntity
import synclife.health.bodytrack.domain.SleepAction
import java.time.LocalDateTime

@Entity
@Table(name = "sleep_tracking")
class SleepTracking (

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, updatable = false)
    var action: SleepAction,

    // BaseEntity
    personId: String,
    datetime: LocalDateTime

) : BaseEntity(){
    init {
        this.personId = personId
        this.datetime = datetime
    }
}