package synclife.health.bodytrack.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import synclife.health.bodytrack.domain.measure.Measurement

interface MeasurementRepository : JpaRepository<Measurement, Long> {
}