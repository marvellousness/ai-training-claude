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
import nova.publish.bazarbooks.feature.auth.forgotpassword.ForgotPasswordIntent
import nova.publish.bazarbooks.feature.auth.forgotpassword.ForgotPasswordMethod
import nova.publish.bazarbooks.feature.auth.forgotpassword.ForgotPasswordStep
import nova.publish.bazarbooks.feature.auth.forgotpassword.ForgotPasswordViewModel
import nova.publish.bazarbooks.feature.auth.signin.SigninIntent
import nova.publish.bazarbooks.feature.auth.signin.SigninViewModel
import nova.publish.bazarbooks.feature.auth.signup.SignupIntent
import nova.publish.bazarbooks.feature.auth.signup.SignupStep
import nova.publish.bazarbooks.feature.auth.signup.SignupViewModel
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
    fun `sign up exposes password requirements while typing`() = runTest(dispatcher) {
        val viewModel = SignupViewModel(SignUpUseCase(repository))

        viewModel.onIntent(SignupIntent.FullNameChanged("John Doe"))
        viewModel.onIntent(SignupIntent.EmailChanged("john@example.com"))
        viewModel.onIntent(SignupIntent.PasswordChanged("short"))

        assertFalse(viewModel.state.value.passwordRequirements.minLength)

        viewModel.onIntent(SignupIntent.PasswordChanged("password1"))

        assertTrue(viewModel.state.value.passwordRequirements.minLength)
        assertTrue(viewModel.state.value.passwordRequirements.hasNumber)
        assertTrue(viewModel.state.value.passwordRequirements.hasLetter)
    }

    @Test
    fun `sign up valid form moves to email verification before account creation`() = runTest(dispatcher) {
        val viewModel = SignupViewModel(SignUpUseCase(repository))

        viewModel.enterValidSignupForm()
        viewModel.onIntent(SignupIntent.Submit)
        advanceUntilIdle()

        assertEquals(SignupStep.EmailVerification, viewModel.state.value.step)
        assertFalse(repository.signUpCalled)
        assertFalse(viewModel.state.value.showSuccess)
    }

    @Test
    fun `sign up completes through simulated email otp mandatory phone and phone otp`() = runTest(dispatcher) {
        val viewModel = SignupViewModel(SignUpUseCase(repository))

        viewModel.enterValidSignupForm()
        viewModel.onIntent(SignupIntent.Submit)
        viewModel.onIntent(SignupIntent.EmailOtpChanged(SignupViewModel.SimulatedOtpCode))
        viewModel.onIntent(SignupIntent.SubmitEmailOtp)
        viewModel.onIntent(SignupIntent.PhoneChanged("+9651234357565"))
        viewModel.onIntent(SignupIntent.SubmitPhone)
        viewModel.onIntent(SignupIntent.PhoneOtpChanged(SignupViewModel.SimulatedOtpCode))
        viewModel.onIntent(SignupIntent.SubmitPhoneOtp)
        advanceUntilIdle()

        assertEquals(SignupStep.Success, viewModel.state.value.step)
        assertTrue(repository.signUpCalled)
        assertFalse(viewModel.state.value.showSuccess)

        viewModel.onIntent(SignupIntent.GetStarted)
        assertTrue(viewModel.state.value.showSuccess)
    }

    @Test
    fun `sign up requires valid phone before phone verification`() = runTest(dispatcher) {
        val viewModel = SignupViewModel(SignUpUseCase(repository))

        viewModel.enterValidSignupForm()
        viewModel.onIntent(SignupIntent.Submit)
        viewModel.onIntent(SignupIntent.EmailOtpChanged(SignupViewModel.SimulatedOtpCode))
        viewModel.onIntent(SignupIntent.SubmitEmailOtp)
        viewModel.onIntent(SignupIntent.PhoneChanged("12"))
        viewModel.onIntent(SignupIntent.SubmitPhone)

        assertEquals(SignupStep.PhoneInput, viewModel.state.value.step)
        assertEquals("Enter a valid phone number", viewModel.state.value.phoneError)
    }

    @Test
    fun `sign up rejects wrong otp and resend regenerates simulated otp`() = runTest(dispatcher) {
        val viewModel = SignupViewModel(SignUpUseCase(repository))

        viewModel.enterValidSignupForm()
        viewModel.onIntent(SignupIntent.Submit)
        val firstOtp = viewModel.state.value.emailOtpToken
        viewModel.onIntent(SignupIntent.EmailOtpChanged("0000"))
        viewModel.onIntent(SignupIntent.SubmitEmailOtp)

        assertEquals(SignupStep.EmailVerification, viewModel.state.value.step)
        assertEquals("Enter the verification code we sent", viewModel.state.value.emailOtpError)

        viewModel.onIntent(SignupIntent.ResendEmailOtp)

        assertTrue(viewModel.state.value.emailOtpToken != firstOtp)
        assertEquals(null, viewModel.state.value.emailOtpError)
    }

    private fun SignupViewModel.enterValidSignupForm() {
        onIntent(SignupIntent.FullNameChanged("John Doe"))
        onIntent(SignupIntent.EmailChanged("john@example.com"))
        onIntent(SignupIntent.PasswordChanged("password1"))
    }

    @Test
    fun `forgot password requires a reset method before continuing`() = runTest(dispatcher) {
        val viewModel = ForgotPasswordViewModel(RequestPasswordResetUseCase(repository))

        viewModel.onIntent(ForgotPasswordIntent.SubmitMethod)

        assertEquals(ForgotPasswordStep.MethodSelection, viewModel.state.value.step)
        assertEquals("Choose email or phone number", viewModel.state.value.methodError)
    }

    @Test
    fun `forgot password email path reaches success`() = runTest(dispatcher) {
        val viewModel = ForgotPasswordViewModel(RequestPasswordResetUseCase(repository))

        viewModel.onIntent(ForgotPasswordIntent.MethodSelected(ForgotPasswordMethod.Email))
        viewModel.onIntent(ForgotPasswordIntent.SubmitMethod)
        viewModel.onIntent(ForgotPasswordIntent.EmailChanged("reader@example.com"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitEmail)
        advanceUntilIdle()
        viewModel.onIntent(ForgotPasswordIntent.OtpChanged(ForgotPasswordViewModel.SimulatedOtpCode))
        viewModel.onIntent(ForgotPasswordIntent.SubmitOtp)
        viewModel.onIntent(ForgotPasswordIntent.NewPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.ConfirmPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitNewPassword)

        assertTrue(repository.resetCalled)
        assertEquals(ForgotPasswordStep.Success, viewModel.state.value.step)
    }

    @Test
    fun `forgot password phone path reaches success`() = runTest(dispatcher) {
        val viewModel = ForgotPasswordViewModel(RequestPasswordResetUseCase(repository))

        viewModel.onIntent(ForgotPasswordIntent.MethodSelected(ForgotPasswordMethod.Phone))
        viewModel.onIntent(ForgotPasswordIntent.SubmitMethod)
        viewModel.onIntent(ForgotPasswordIntent.PhoneChanged("+9651234357565"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitPhone)
        viewModel.onIntent(ForgotPasswordIntent.OtpChanged(ForgotPasswordViewModel.SimulatedOtpCode))
        viewModel.onIntent(ForgotPasswordIntent.SubmitOtp)
        viewModel.onIntent(ForgotPasswordIntent.NewPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.ConfirmPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitNewPassword)

        assertFalse(repository.resetCalled)
        assertEquals(ForgotPasswordStep.Success, viewModel.state.value.step)
    }

    @Test
    fun `forgot password otp mismatch and resend stay on verification`() = runTest(dispatcher) {
        val viewModel = ForgotPasswordViewModel(RequestPasswordResetUseCase(repository))

        viewModel.onIntent(ForgotPasswordIntent.MethodSelected(ForgotPasswordMethod.Phone))
        viewModel.onIntent(ForgotPasswordIntent.SubmitMethod)
        viewModel.onIntent(ForgotPasswordIntent.PhoneChanged("+9651234357565"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitPhone)
        val firstToken = viewModel.state.value.otpToken

        viewModel.onIntent(ForgotPasswordIntent.OtpChanged("0000"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitOtp)

        assertEquals(ForgotPasswordStep.PhoneVerification, viewModel.state.value.step)
        assertEquals("Enter the verification code we sent", viewModel.state.value.otpError)

        viewModel.onIntent(ForgotPasswordIntent.ResendOtp)

        assertTrue(viewModel.state.value.otpToken != firstToken)
        assertEquals("", viewModel.state.value.otp)
        assertEquals(null, viewModel.state.value.otpError)
    }

    @Test
    fun `forgot password confirm password mismatch blocks success`() = runTest(dispatcher) {
        val viewModel = ForgotPasswordViewModel(RequestPasswordResetUseCase(repository))

        viewModel.onIntent(ForgotPasswordIntent.MethodSelected(ForgotPasswordMethod.Email))
        viewModel.onIntent(ForgotPasswordIntent.SubmitMethod)
        viewModel.onIntent(ForgotPasswordIntent.EmailChanged("reader@example.com"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitEmail)
        advanceUntilIdle()
        viewModel.onIntent(ForgotPasswordIntent.OtpChanged(ForgotPasswordViewModel.SimulatedOtpCode))
        viewModel.onIntent(ForgotPasswordIntent.SubmitOtp)
        viewModel.onIntent(ForgotPasswordIntent.NewPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.ConfirmPasswordChanged("password2"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitNewPassword)

        assertEquals(ForgotPasswordStep.NewPassword, viewModel.state.value.step)
        assertEquals("Passwords do not match", viewModel.state.value.confirmPasswordError)
    }

    @Test
    fun `forgot password login action requests sign in navigation`() = runTest(dispatcher) {
        val viewModel = ForgotPasswordViewModel(RequestPasswordResetUseCase(repository))

        viewModel.onIntent(ForgotPasswordIntent.MethodSelected(ForgotPasswordMethod.Phone))
        viewModel.onIntent(ForgotPasswordIntent.SubmitMethod)
        viewModel.onIntent(ForgotPasswordIntent.PhoneChanged("+9651234357565"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitPhone)
        viewModel.onIntent(ForgotPasswordIntent.OtpChanged(ForgotPasswordViewModel.SimulatedOtpCode))
        viewModel.onIntent(ForgotPasswordIntent.SubmitOtp)
        viewModel.onIntent(ForgotPasswordIntent.NewPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.ConfirmPasswordChanged("password1"))
        viewModel.onIntent(ForgotPasswordIntent.SubmitNewPassword)
        viewModel.onIntent(ForgotPasswordIntent.Login)

        assertTrue(viewModel.state.value.navigateSignIn)
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
