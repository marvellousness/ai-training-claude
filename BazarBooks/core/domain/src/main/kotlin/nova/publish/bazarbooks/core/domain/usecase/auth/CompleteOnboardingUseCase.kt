package nova.publish.bazarbooks.core.domain.usecase.auth

import nova.publish.bazarbooks.core.domain.repository.AuthRepository

class CompleteOnboardingUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.completeOnboarding()
}
