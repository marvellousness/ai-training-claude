package nova.publish.bazarbooks.core.domain.usecase.profile

import nova.publish.bazarbooks.core.domain.repository.NotificationRepository

class MarkNotificationReadUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(id: String) = repository.markRead(id)
}
