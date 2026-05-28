package nova.publish.bazarbooks.core.data.repository

import kotlinx.coroutines.flow.map
import nova.publish.bazarbooks.core.data.preferences.AuthPreferencesDataSource
import nova.publish.bazarbooks.core.domain.model.AuthSession
import nova.publish.bazarbooks.core.domain.repository.AuthRepository

class DataStoreAuthRepository(
    private val preferences: AuthPreferencesDataSource,
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        preferences.setAuthSession(AuthSession.Authenticated)
        return Result.success(Unit)
    }

    override suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> {
        preferences.setAuthSession(AuthSession.Authenticated)
        return Result.success(Unit)
    }

    override suspend fun continueAsGuest() {
        preferences.setAuthSession(AuthSession.Guest)
    }

    override suspend fun requestPasswordReset(email: String): Result<Unit> = Result.success(Unit)

    override suspend fun logout() {
        preferences.setAuthSession(AuthSession.Unauthenticated)
    }

    override suspend fun completeOnboarding() {
        preferences.setOnboardingCompleted(true)
    }

    override fun observeOnboardingCompleted() = preferences.onboardingCompleted

    override fun observeSessionActive() = preferences.authSession.map { session ->
        session == AuthSession.Authenticated || session == AuthSession.Guest
    }

    override fun observeSessionState() = preferences.authSession
}
