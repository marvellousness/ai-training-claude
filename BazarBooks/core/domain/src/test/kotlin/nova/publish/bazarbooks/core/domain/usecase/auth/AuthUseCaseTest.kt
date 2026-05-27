package nova.publish.bazarbooks.core.domain.usecase.auth

import kotlinx.coroutines.test.runTest
import nova.publish.bazarbooks.core.domain.repository.AuthRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthUseCaseTest {
    @Test
    fun `login delegates credentials to repository and returns session result`() = runTest {
        val repository = RecordingAuthRepository()
        val useCase = LoginUseCase(repository)

        val result = useCase("reader@example.com", "bookpass1")

        assertTrue(result.isSuccess)
        assertEquals("reader@example.com", repository.email)
        assertEquals("bookpass1", repository.password)
    }

    private class RecordingAuthRepository : AuthRepository {
        var email: String? = null
        var password: String? = null

        override suspend fun login(email: String, password: String): Result<Unit> {
            this.email = email
            this.password = password
            return Result.success(Unit)
        }

        override suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> = Result.success(Unit)
        override suspend fun requestPasswordReset(email: String): Result<Unit> = Result.success(Unit)
        override suspend fun logout() = Unit
        override suspend fun completeOnboarding() = Unit
        override fun observeOnboardingCompleted() = kotlinx.coroutines.flow.flowOf(false)
        override fun observeSessionActive() = kotlinx.coroutines.flow.flowOf(false)
    }
}
