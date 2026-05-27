package nova.publish.bazarbooks.core.domain.usecase.auth

import nova.publish.bazarbooks.core.domain.repository.AuthRepository

class RequestPasswordResetUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> = repository.requestPasswordReset(email)
}
