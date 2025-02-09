package synclife.health.bodytrack.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import synclife.health.bodytrack.domain.sleep.SleepTracking
import java.time.LocalDateTime

interface SleepTrackingRepository : JpaRepository<SleepTracking, Long> {

    @Query(nativeQuery = true, value = """
        SELECT DISTINCT st.person_id 
        FROM sleep_tracking st
    """)
    fun findDistinctPersonId(): List<String>

    @Query(nativeQuery = true, value = """
        SELECT * 
	        FROM sleep_tracking st 
        WHERE computed = FALSE
	        AND `action` = 'WAKE_UP'
	        AND person_id = :personId
        ORDER BY `datetime` DESC
        LIMIT 1
    """)
    fun findLastWakeUpByPersonId(personId: String): SleepTracking?

    @Query(nativeQuery = true, value = """
        SELECT * 
	        FROM sleep_tracking st 
        WHERE `action` = 'SLEEP'
	        AND person_id = :personId
	        AND `datetime` < :dateTime
        ORDER BY `datetime` DESC
        LIMIT 1
    """)
    fun findLastSleepByPersonIdAndDatetime(personId: String, dateTime: LocalDateTime): SleepTracking?
}