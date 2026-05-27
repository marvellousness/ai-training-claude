package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.domain.model.Notification
import nova.publish.bazarbooks.core.domain.repository.NotificationRepository

class FakeNotificationRepository : NotificationRepository {
    private var notifications = listOf(
        Notification("n1", "Order update", "Your order is being prepared.", false, System.currentTimeMillis(), "order-1"),
        Notification("n2", "New arrivals", "Fresh Android books are available.", false, System.currentTimeMillis()),
    )

    override suspend fun getNotifications(): List<Notification> = notifications

    override suspend fun markRead(id: String) {
        notifications = notifications.map { if (it.id == id) it.copy(isRead = true) else it }
    }
}
