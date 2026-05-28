package nova.publish.bazarbooks.core.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import nova.publish.bazarbooks.core.domain.model.AuthSession
import nova.publish.bazarbooks.core.domain.repository.AuthRepository

class FakeAuthRepository : AuthRepository {
    private val onboardingCompleted = MutableStateFlow(false)
    private val sessionActive = MutableStateFlow(false)

    override suspend fun login(email: String, password: String): Result<Unit> {
        sessionActive.value = true
        return Result.success(Unit)
    }

    override suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> {
        sessionActive.value = true
        return Result.success(Unit)
    }

    override suspend fun continueAsGuest() {
        sessionActive.value = true
    }

    override suspend fun requestPasswordReset(email: String): Result<Unit> = Result.success(Unit)

    override suspend fun logout() {
        sessionActive.value = false
    }

    override suspend fun completeOnboarding() {
        onboardingCompleted.value = true
    }

    override fun observeOnboardingCompleted() = onboardingCompleted.asStateFlow()
    override fun observeSessionActive() = sessionActive.asStateFlow()
    override fun observeSessionState() = sessionActive.map { active ->
        if (active) AuthSession.Authenticated else AuthSession.Unauthenticated
    }
}
