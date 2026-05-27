package nova.publish.bazarbooks.core.domain.usecase.auth

import nova.publish.bazarbooks.core.domain.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logout()
}
