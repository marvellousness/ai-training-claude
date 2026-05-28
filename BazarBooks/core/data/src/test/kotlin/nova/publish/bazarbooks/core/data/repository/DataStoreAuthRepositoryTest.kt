package nova.publish.bazarbooks.core.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import nova.publish.bazarbooks.core.data.preferences.AuthPreferencesDataSource
import nova.publish.bazarbooks.core.domain.model.AuthSession
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataStoreAuthRepositoryTest {
    @Test
    fun `onboarding and session survive repository recreation`() = runTest {
        val preferences = InMemoryAuthPreferencesDataSource()
        val repository = DataStoreAuthRepository(preferences)

        assertFalse(repository.observeOnboardingCompleted().first())
        assertEquals(AuthSession.Unauthenticated, repository.observeSessionState().first())

        repository.completeOnboarding()
        repository.continueAsGuest()

        val recreated = DataStoreAuthRepository(preferences)

        assertTrue(recreated.observeOnboardingCompleted().first())
        assertTrue(recreated.observeSessionActive().first())
        assertEquals(AuthSession.Guest, recreated.observeSessionState().first())
    }

    @Test
    fun `login stores authenticated session and logout clears it`() = runTest {
        val repository = DataStoreAuthRepository(InMemoryAuthPreferencesDataSource())

        repository.login("reader@example.com", "password123")

        assertEquals(AuthSession.Authenticated, repository.observeSessionState().first())

        repository.logout()

        assertEquals(AuthSession.Unauthenticated, repository.observeSessionState().first())
    }

    private class InMemoryAuthPreferencesDataSource : AuthPreferencesDataSource {
        override val onboardingCompleted = MutableStateFlow(false)
        override val authSession = MutableStateFlow(AuthSession.Unauthenticated)

        override suspend fun setOnboardingCompleted(completed: Boolean) {
            onboardingCompleted.value = completed
        }

        override suspend fun setAuthSession(session: AuthSession) {
            authSession.value = session
        }
    }
}
