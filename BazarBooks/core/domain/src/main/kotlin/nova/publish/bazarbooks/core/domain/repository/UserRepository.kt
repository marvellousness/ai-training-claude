package nova.publish.bazarbooks.core.domain.repository

import nova.publish.bazarbooks.core.domain.model.User

interface UserRepository {
    suspend fun getProfile(): User
    suspend fun updateProfile(user: User): User
}
