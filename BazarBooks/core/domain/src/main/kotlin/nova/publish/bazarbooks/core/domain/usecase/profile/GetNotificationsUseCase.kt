package nova.publish.bazarbooks.core.domain.usecase.profile

import nova.publish.bazarbooks.core.domain.repository.NotificationRepository

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke() = repository.getNotifications()
}
