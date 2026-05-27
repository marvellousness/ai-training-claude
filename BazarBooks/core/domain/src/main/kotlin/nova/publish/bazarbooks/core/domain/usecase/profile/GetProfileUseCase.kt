package nova.publish.bazarbooks.core.domain.usecase.profile

import nova.publish.bazarbooks.core.domain.repository.UserRepository

class GetProfileUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.getProfile()
}
