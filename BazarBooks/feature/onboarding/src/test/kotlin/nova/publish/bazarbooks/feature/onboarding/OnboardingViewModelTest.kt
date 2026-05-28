package nova.publish.bazarbooks.feature.onboarding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nova.publish.bazarbooks.core.domain.model.AuthSession
import nova.publish.bazarbooks.core.domain.repository.AuthRepository
import nova.publish.bazarbooks.core.domain.usecase.auth.CompleteOnboardingUseCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: RecordingAuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = RecordingAuthRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `page change updates state`() {
        val viewModel = OnboardingViewModel(CompleteOnboardingUseCase(repository))

        viewModel.onIntent(OnboardingIntent.PageChanged(1))

        assertEquals(1, viewModel.state.value.pageIndex)
    }

    @Test
    fun `get started persists onboarding completion and navigates to sign in`() = runTest(dispatcher) {
        val viewModel = OnboardingViewModel(CompleteOnboardingUseCase(repository))

        viewModel.onIntent(OnboardingIntent.GetStartedClicked)
        advanceUntilIdle()

        assertTrue(repository.onboarding.value)
        assertEquals(OnboardingEffect.NavigateToSignIn, viewModel.effect.first())
    }

    private class RecordingAuthRepository : AuthRepository {
        val onboarding = MutableStateFlow(false)
        private val session = MutableStateFlow(AuthSession.Unauthenticated)

        override suspend fun login(email: String, password: String): Result<Unit> = Result.success(Unit)
        override suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> = Result.success(Unit)
        override suspend fun continueAsGuest() {
            session.value = AuthSession.Guest
        }

        override suspend fun requestPasswordReset(email: String): Result<Unit> = Result.success(Unit)
        override suspend fun logout() {
            session.value = AuthSession.Unauthenticated
        }

        override suspend fun completeOnboarding() {
            onboarding.value = true
        }

        override fun observeOnboardingCompleted(): Flow<Boolean> = onboarding
        override fun observeSessionActive(): Flow<Boolean> = session.map { it != AuthSession.Unauthenticated }
        override fun observeSessionState(): Flow<AuthSession> = session
    }
}
