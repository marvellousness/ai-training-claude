package nova.publish.bazarbooks.core.domain.usecase.auth

import nova.publish.bazarbooks.core.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(fullName: String, email: String, password: String): Result<Unit> = repository.signUp(fullName, email, password)
}
