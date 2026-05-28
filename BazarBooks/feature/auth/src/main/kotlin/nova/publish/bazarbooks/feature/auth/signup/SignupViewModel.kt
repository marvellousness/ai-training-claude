package nova.publish.bazarbooks.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nova.publish.bazarbooks.core.common.validation.EmailValidator
import nova.publish.bazarbooks.core.common.validation.PasswordValidator
import nova.publish.bazarbooks.core.common.validation.ValidationResult
import nova.publish.bazarbooks.core.domain.usecase.auth.SignUpUseCase

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {
    companion object {
        const val SimulatedOtpCode = "2850"
        private const val OtpLength = 4
        private const val MinPhoneDigits = 8
    }

    private val _state = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState> = _state.asStateFlow()

    fun onIntent(intent: SignupIntent) {
        when (intent) {
            is SignupIntent.FullNameChanged -> _state.update { it.copy(fullName = intent.value, fullNameError = null) }
            is SignupIntent.EmailChanged -> _state.update { it.copy(email = intent.value, emailError = null) }
            is SignupIntent.PasswordChanged -> _state.update {
                it.copy(
                    password = intent.value,
                    passwordError = null,
                    passwordRequirements = intent.value.toRequirements(),
                )
            }
            is SignupIntent.EmailOtpChanged -> _state.update {
                it.copy(emailOtp = intent.value.filter(Char::isDigit).take(OtpLength), emailOtpError = null)
            }
            is SignupIntent.PhoneChanged -> _state.update { it.copy(phone = intent.value, phoneError = null) }
            is SignupIntent.PhoneOtpChanged -> _state.update {
                it.copy(phoneOtp = intent.value.filter(Char::isDigit).take(OtpLength), phoneOtpError = null)
            }
            SignupIntent.Submit -> submit()
            SignupIntent.SubmitEmailOtp -> submitEmailOtp()
            SignupIntent.SubmitPhone -> submitPhone()
            SignupIntent.SubmitPhoneOtp -> submitPhoneOtp()
            SignupIntent.ResendEmailOtp -> resendEmailOtp()
            SignupIntent.ResendPhoneOtp -> resendPhoneOtp()
            SignupIntent.GetStarted -> _state.update { it.copy(showSuccess = true) }
        }
    }

    private fun submit() {
        val current = _state.value
        val emailResult = EmailValidator.validate(current.email)
        val passwordResult = PasswordValidator.validate(current.password)
        val nameError = if (current.fullName.isBlank()) "Name is required" else null
        if (nameError != null || emailResult is ValidationResult.Invalid || passwordResult is ValidationResult.Invalid) {
            _state.update {
                it.copy(
                    step = SignupStep.Form,
                    fullNameError = nameError,
                    emailError = (emailResult as? ValidationResult.Invalid)?.message,
                    passwordError = (passwordResult as? ValidationResult.Invalid)?.message,
                )
            }
            return
        }

        _state.update {
            it.copy(
                step = SignupStep.EmailVerification,
                emailOtp = "",
                emailOtpError = null,
                emailOtpToken = it.emailOtpToken + 1,
                showSuccess = false,
            )
        }
    }

    private fun submitEmailOtp() {
        val current = _state.value
        if (current.emailOtp != SimulatedOtpCode) {
            _state.update { it.copy(emailOtpError = "Enter the verification code we sent") }
            return
        }
        _state.update {
            it.copy(
                step = SignupStep.PhoneInput,
                emailOtpError = null,
                phone = "",
                phoneError = null,
            )
        }
    }

    private fun submitPhone() {
        val phone = _state.value.phone.filter(Char::isDigit)
        if (phone.length < MinPhoneDigits) {
            _state.update { it.copy(phoneError = "Enter a valid phone number") }
            return
        }
        _state.update {
            it.copy(
                step = SignupStep.PhoneVerification,
                phoneError = null,
                phoneOtp = "",
                phoneOtpError = null,
                phoneOtpToken = it.phoneOtpToken + 1,
            )
        }
    }

    private fun submitPhoneOtp() {
        val current = _state.value
        if (current.phoneOtp != SimulatedOtpCode) {
            _state.update { it.copy(phoneOtpError = "Enter the verification code we sent") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = signUpUseCase(current.fullName.trim(), current.email.trim(), current.password)
            _state.update {
                it.copy(
                    step = if (result.isSuccess) SignupStep.Success else SignupStep.PhoneVerification,
                    isLoading = false,
                    passwordError = result.exceptionOrNull()?.message,
                )
            }
        }
    }

    private fun resendEmailOtp() {
        _state.update {
            it.copy(
                emailOtp = "",
                emailOtpError = null,
                emailOtpToken = it.emailOtpToken + 1,
            )
        }
    }

    private fun resendPhoneOtp() {
        _state.update {
            it.copy(
                phoneOtp = "",
                phoneOtpError = null,
                phoneOtpToken = it.phoneOtpToken + 1,
            )
        }
    }

    private fun String.toRequirements() = PasswordRequirements(
        minLength = length >= 8,
        hasNumber = any(Char::isDigit),
        hasLetter = any(Char::isLetter),
    )
}
