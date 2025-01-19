package synclife.health.bodytrack.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import synclife.health.bodytrack.domain.sleep.SleepTracking

interface SleepTrackingRepository : JpaRepository<SleepTracking, Long> {
}