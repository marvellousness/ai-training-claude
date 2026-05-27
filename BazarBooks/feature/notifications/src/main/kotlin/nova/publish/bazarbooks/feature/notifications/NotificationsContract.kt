package nova.publish.bazarbooks.feature.notifications

import nova.publish.bazarbooks.feature.notifications.model.NotificationUiModel

data class NotificationsState(val items: List<NotificationUiModel> = emptyList())
