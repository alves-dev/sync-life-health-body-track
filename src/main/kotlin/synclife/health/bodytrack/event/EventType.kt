package synclife.health.bodytrack.event

fun interface EventType {
    fun getEventFlow(): EventFlow
}