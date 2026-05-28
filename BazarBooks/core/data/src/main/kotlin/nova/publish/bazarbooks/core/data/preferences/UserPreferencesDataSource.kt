package nova.publish.bazarbooks.core.data.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nova.publish.bazarbooks.core.domain.model.AuthSession

class UserPreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
) : AuthPreferencesDataSource {
    override val onboardingCompleted: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED] ?: false
    }

    override val authSession: Flow<AuthSession> = dataStore.data.map { preferences ->
        preferences[AUTH_SESSION]?.let(AuthSession::valueOf) ?: AuthSession.Unauthenticated
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    override suspend fun setAuthSession(session: AuthSession) {
        dataStore.edit { preferences ->
            preferences[AUTH_SESSION] = session.name
        }
    }

    private companion object {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val AUTH_SESSION = stringPreferencesKey("auth_session")
    }
}
