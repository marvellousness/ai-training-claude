package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.domain.model.User
import nova.publish.bazarbooks.core.domain.repository.UserRepository

class FakeUserRepository : UserRepository {
    private var user = User("u1", "Bazar Reader", "reader@example.com")

    override suspend fun getProfile(): User = user

    override suspend fun updateProfile(user: User): User {
        this.user = user
        return user
    }
}
