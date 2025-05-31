package synclife.health.bodytrack.domain.measure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import synclife.health.bodytrack.event.v3.EventHealthBodyMeasurementV1
import synclife.health.bodytrack.infrastructure.repository.MeasurementRepository

@Service
class MeasureService {

    @Autowired
    private lateinit var measurementRepository: MeasurementRepository

    @Async
    @EventListener
    fun processEventSleep(event: EventHealthBodyMeasurementV1) {
        val measure = Measurement(event.data.measure, event.data.value, event.data.personId, event.data.datetime)
        measurementRepository.save(measure)
    }
}