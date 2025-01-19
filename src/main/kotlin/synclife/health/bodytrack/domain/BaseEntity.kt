package synclife.health.bodytrack.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long = 0

    @Column(name = "person_id", nullable = false, length = 30)
    lateinit var personId: String

    @Column(name = "datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    lateinit var datetime: LocalDateTime
}