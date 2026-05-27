package nova.publish.bazarbooks.core.domain.repository

import nova.publish.bazarbooks.core.domain.model.Notification

interface NotificationRepository {
    suspend fun getNotifications(): List<Notification>
    suspend fun markRead(id: String)
}
