package nova.publish.bazarbooks.core.domain.model

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val isRead: Boolean,
    val createdAtEpochMillis: Long,
    val orderId: String? = null,
)
