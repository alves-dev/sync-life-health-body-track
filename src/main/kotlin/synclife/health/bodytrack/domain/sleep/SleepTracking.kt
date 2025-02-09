package synclife.health.bodytrack.domain.sleep

import jakarta.persistence.*
import synclife.health.bodytrack.domain.BaseEntity
import synclife.health.bodytrack.domain.SleepAction
import synclife.health.bodytrack.utils.OperationResult
import java.time.Duration
import java.time.LocalDateTime

@Entity
@Table(name = "sleep_tracking")
class SleepTracking (

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, updatable = false)
    var action: SleepAction,

    @Column(name = "computed", nullable = false)
    var computed: Boolean,

    @Column(name = "minutes_slept", nullable = false)
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
        computed = false,
        minutesSlept = 0,
        personId,
        datetime
    )

    private fun markAsComputed(minutes: Int) {
        this.computed = true
        if (SleepAction.WAKE_UP == this.action)
            this.minutesSlept = minutes
    }

    companion object {
        fun markAsComputed(sleep: SleepTracking, wakeUp: SleepTracking): OperationResult {
            val duration = Duration.between(sleep.datetime, wakeUp.datetime)
            val minutes = duration.toMinutes().toInt()

            if (!validMinutes(minutes)) return OperationResult.ofFailure("Tempo de sono não atende as regras")

            sleep.markAsComputed(minutes)
            wakeUp.markAsComputed(minutes)

            return OperationResult.ofSuccess();
        }

        private fun validMinutes(minutes : Int): Boolean{
            val minMinutes = 60
            val maxMinutes = 60 * 10
            return minutes in minMinutes..maxMinutes
        }
    }
}