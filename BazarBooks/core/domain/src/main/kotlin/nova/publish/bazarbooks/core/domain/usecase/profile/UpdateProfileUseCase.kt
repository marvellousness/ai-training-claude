package nova.publish.bazarbooks.core.domain.usecase.profile

import nova.publish.bazarbooks.core.domain.model.User
import nova.publish.bazarbooks.core.domain.repository.UserRepository

class UpdateProfileUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) = repository.updateProfile(user)
}
