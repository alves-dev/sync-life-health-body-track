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

    @Column(name = "accounted")
    var accounted: Boolean,

    @Column(name = "minutes_slept")
    var minutesSlept: Int,

    // BaseEntity
    personId: String,
    datetime: LocalDateTime

) : BaseEntity(){
    init {
        this.personId = personId
        this.datetime = datetime
    }

    constructor(action: SleepAction, personId: String, datetime: LocalDateTime) : this(
        action,
        accounted = false,
        minutesSlept = 0,
        personId,
        datetime
    )
}