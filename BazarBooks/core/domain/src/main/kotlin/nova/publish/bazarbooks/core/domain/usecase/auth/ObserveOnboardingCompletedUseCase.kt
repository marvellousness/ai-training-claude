package nova.publish.bazarbooks.core.domain.usecase.auth

import nova.publish.bazarbooks.core.domain.repository.AuthRepository

class ObserveOnboardingCompletedUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.observeOnboardingCompleted()
}
