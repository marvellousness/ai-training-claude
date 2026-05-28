package nova.publish.bazarbooks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nova.publish.bazarbooks.core.domain.model.AuthSession
import nova.publish.bazarbooks.core.domain.repository.AuthRepository
import nova.publish.bazarbooks.core.domain.usecase.auth.ObserveOnboardingCompletedUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.ObserveSessionUseCase
import nova.publish.bazarbooks.core.navigation.AppRoute
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppStartupViewModelTest {
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
    fun `starts at onboarding until onboarding is completed`() = runTest(dispatcher) {
        val viewModel = viewModel()
        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(AppRoute.Onboarding.route, viewModel.state.value.startDestination)
    }

    @Test
    fun `starts at sign in when onboarding is complete without active session`() = runTest(dispatcher) {
        repository.onboarding.value = true
        repository.session.value = AuthSession.Unauthenticated
        val viewModel = viewModel()
        advanceUntilIdle()

        assertEquals(AppRoute.SignIn.route, viewModel.state.value.startDestination)
    }

    @Test
    fun `starts at home when onboarding is complete with guest or authenticated session`() = runTest(dispatcher) {
        repository.onboarding.value = true
        repository.session.value = AuthSession.Guest
        val viewModel = viewModel()
        advanceUntilIdle()

        assertEquals(AppRoute.Home.route, viewModel.state.value.startDestination)
    }

    private fun viewModel() = AppStartupViewModel(
        ObserveOnboardingCompletedUseCase(repository),
        ObserveSessionUseCase(repository),
    )

    private class RecordingAuthRepository : AuthRepository {
        val onboarding = MutableStateFlow(false)
        val session = MutableStateFlow(AuthSession.Unauthenticated)

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
        override fun observeSessionActive(): Flow<Boolean> = session.map { it == AuthSession.Authenticated || it == AuthSession.Guest }
        override fun observeSessionState(): Flow<AuthSession> = session
    }
}
