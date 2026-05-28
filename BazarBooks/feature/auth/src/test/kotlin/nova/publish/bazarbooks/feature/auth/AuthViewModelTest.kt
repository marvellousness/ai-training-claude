package nova.publish.bazarbooks.feature.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import nova.publish.bazarbooks.core.domain.model.AuthSession
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nova.publish.bazarbooks.core.domain.repository.AuthRepository
import nova.publish.bazarbooks.core.domain.usecase.auth.ContinueAsGuestUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.LoginUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.RequestPasswordResetUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.SignUpUseCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
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
    fun `sign in validates email before login`() = runTest(dispatcher) {
        val viewModel = SigninViewModel(LoginUseCase(repository), ContinueAsGuestUseCase(repository))

        viewModel.onIntent(SigninIntent.EmailChanged("bad-email"))
        viewModel.onIntent(SigninIntent.PasswordChanged("password123"))
        viewModel.onIntent(SigninIntent.Submit)
        advanceUntilIdle()

        assertEquals("Enter a valid email", viewModel.state.value.emailError)
        assertFalse(repository.loginCalled)
        assertFalse(viewModel.state.value.navigateHome)
    }

    @Test
    fun `sign in submits valid email credentials and navigates home`() = runTest(dispatcher) {
        val viewModel = SigninViewModel(LoginUseCase(repository), ContinueAsGuestUseCase(repository))

        viewModel.onIntent(SigninIntent.EmailChanged("reader@example.com"))
        viewModel.onIntent(SigninIntent.PasswordChanged("password123"))
        viewModel.onIntent(SigninIntent.Submit)
        advanceUntilIdle()

        assertTrue(repository.loginCalled)
        assertEquals("reader@example.com", repository.lastLoginEmail)
        assertTrue(viewModel.state.value.navigateHome)
    }

    @Test
    fun `guest intent navigates home without social or phone auth`() = runTest(dispatcher) {
        val viewModel = SigninViewModel(LoginUseCase(repository), ContinueAsGuestUseCase(repository))

        viewModel.onIntent(SigninIntent.ContinueAsGuest)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.navigateHome)
        assertFalse(repository.loginCalled)
        assertTrue(repository.guestCalled)
    }

    @Test
    fun `sign up exposes password requirements and submits valid account`() = runTest(dispatcher) {
        val viewModel = SignupViewModel(SignUpUseCase(repository))

        viewModel.onIntent(SignupIntent.FullNameChanged("John Doe"))
        viewModel.onIntent(SignupIntent.EmailChanged("john@example.com"))
        viewModel.onIntent(SignupIntent.PasswordChanged("short"))

        assertFalse(viewModel.state.value.passwordRequirements.minLength)

        viewModel.onIntent(SignupIntent.PasswordChanged("password1"))
        viewModel.onIntent(SignupIntent.Submit)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.passwordRequirements.minLength)
        assertTrue(viewModel.state.value.passwordRequirements.hasNumber)
        assertTrue(repository.signUpCalled)
        assertTrue(viewModel.state.value.showSuccess)
    }

    @Test
    fun `forgot password email flow reaches success`() = runTest(dispatcher) {
        val viewModel = ForgotPasswordViewModel(RequestPasswordResetUseCase(repository))

        viewModel.onIntent(ForgotPasswordIntent.EmailChanged("reader@example.com"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitEmail)
        advanceUntilIdle()
        viewModel.onIntent(ForgotPasswordIntent.CodeChanged("2850"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitCode)
        viewModel.onIntent(ForgotPasswordIntent.NewPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitNewPassword)

        assertTrue(repository.resetCalled)
        assertEquals(ForgotPasswordStep.Success, viewModel.state.value.step)
    }

    private class RecordingAuthRepository : AuthRepository {
        private val onboarding = MutableStateFlow(false)
        private val session = MutableStateFlow(false)
        var loginCalled = false
        var signUpCalled = false
        var resetCalled = false
        var guestCalled = false
        var lastLoginEmail: String? = null

        override suspend fun login(email: String, password: String): Result<Unit> {
            loginCalled = true
            lastLoginEmail = email
            session.value = true
            return Result.success(Unit)
        }

        override suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> {
            signUpCalled = true
            session.value = true
            return Result.success(Unit)
        }

        override suspend fun requestPasswordReset(email: String): Result<Unit> {
            resetCalled = true
            return Result.success(Unit)
        }

        override suspend fun continueAsGuest() {
            guestCalled = true
            session.value = true
        }

        override suspend fun logout() {
            session.value = false
        }

        override suspend fun completeOnboarding() {
            onboarding.value = true
        }

        override fun observeOnboardingCompleted(): Flow<Boolean> = onboarding
        override fun observeSessionActive(): Flow<Boolean> = session
        override fun observeSessionState(): Flow<AuthSession> = session.map { active ->
            if (active) AuthSession.Authenticated else AuthSession.Unauthenticated
        }
    }
}
