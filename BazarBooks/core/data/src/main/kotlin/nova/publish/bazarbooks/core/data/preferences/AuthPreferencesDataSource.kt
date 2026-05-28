package nova.publish.bazarbooks.core.data.preferences

import kotlinx.coroutines.flow.Flow
import nova.publish.bazarbooks.core.domain.model.AuthSession

interface AuthPreferencesDataSource {
    val onboardingCompleted: Flow<Boolean>
    val authSession: Flow<AuthSession>

    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setAuthSession(session: AuthSession)
}
