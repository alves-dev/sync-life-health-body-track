package synclife.health.bodytrack.utils

data class OperationResult(
    val status: Boolean,
    val reason: String

) {
    companion object {
        fun ofSuccess(): OperationResult {
            return OperationResult(status = true, reason = "")
        }

        fun ofFailure(reason: String): OperationResult {
            return OperationResult(status = false, reason = reason)
        }
    }
}
