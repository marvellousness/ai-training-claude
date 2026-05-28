package nova.publish.bazarbooks.core.domain.repository

import kotlinx.coroutines.flow.Flow
import nova.publish.bazarbooks.core.domain.model.AuthSession

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signUp(fullName: String, email: String, password: String): Result<Unit>
    suspend fun continueAsGuest()
    suspend fun requestPasswordReset(email: String): Result<Unit>
    suspend fun logout()
    suspend fun completeOnboarding()
    fun observeOnboardingCompleted(): Flow<Boolean>
    fun observeSessionActive(): Flow<Boolean>
    fun observeSessionState(): Flow<AuthSession>
}
