package nova.publish.bazarbooks.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signUp(fullName: String, email: String, password: String): Result<Unit>
    suspend fun requestPasswordReset(email: String): Result<Unit>
    suspend fun logout()
    suspend fun completeOnboarding()
    fun observeOnboardingCompleted(): Flow<Boolean>
    fun observeSessionActive(): Flow<Boolean>
}
