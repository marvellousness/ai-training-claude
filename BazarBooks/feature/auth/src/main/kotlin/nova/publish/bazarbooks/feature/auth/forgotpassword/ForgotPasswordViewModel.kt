package nova.publish.bazarbooks.feature.auth.forgotpassword

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
import nova.publish.bazarbooks.core.domain.usecase.auth.RequestPasswordResetUseCase

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val requestPasswordResetUseCase: RequestPasswordResetUseCase,
) : ViewModel() {
    companion object {
        const val SimulatedOtpCode = "2850"
        private const val OtpLength = 4
        private const val MinPhoneDigits = 8
    }

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            is ForgotPasswordIntent.MethodSelected -> _state.update {
                it.copy(selectedMethod = intent.value, methodError = null)
            }
            ForgotPasswordIntent.SubmitMethod -> submitMethod()
            is ForgotPasswordIntent.EmailChanged -> _state.update { it.copy(email = intent.value, emailError = null) }
            is ForgotPasswordIntent.PhoneChanged -> _state.update { it.copy(phone = intent.value, phoneError = null) }
            ForgotPasswordIntent.SubmitEmail -> submitEmail()
            ForgotPasswordIntent.SubmitPhone -> submitPhone()
            is ForgotPasswordIntent.OtpChanged -> _state.update {
                it.copy(otp = intent.value.filter(Char::isDigit).take(OtpLength), otpError = null)
            }
            ForgotPasswordIntent.SubmitOtp -> submitOtp()
            ForgotPasswordIntent.ResendOtp -> resendOtp()
            is ForgotPasswordIntent.NewPasswordChanged -> _state.update { it.copy(newPassword = intent.value, passwordError = null) }
            is ForgotPasswordIntent.ConfirmPasswordChanged -> _state.update {
                it.copy(confirmPassword = intent.value, confirmPasswordError = null)
            }
            ForgotPasswordIntent.SubmitNewPassword -> submitNewPassword()
            ForgotPasswordIntent.Login -> _state.update { it.copy(navigateSignIn = true) }
            ForgotPasswordIntent.NavigationHandled -> _state.update { it.copy(navigateSignIn = false) }
        }
    }

    private fun submitMethod() {
        when (_state.value.selectedMethod) {
            ForgotPasswordMethod.Email -> _state.update { it.copy(step = ForgotPasswordStep.EmailInput, methodError = null) }
            ForgotPasswordMethod.Phone -> _state.update { it.copy(step = ForgotPasswordStep.PhoneInput, methodError = null) }
            null -> _state.update { it.copy(methodError = "Choose email or phone number") }
        }
    }

    private fun submitEmail() {
        val emailResult = EmailValidator.validate(_state.value.email)
        if (emailResult is ValidationResult.Invalid) {
            _state.update { it.copy(emailError = emailResult.message) }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = requestPasswordResetUseCase(_state.value.email.trim())
            _state.update {
                it.copy(
                    isLoading = false,
                    step = if (result.isSuccess) ForgotPasswordStep.EmailVerification else ForgotPasswordStep.EmailInput,
                    emailError = result.exceptionOrNull()?.message,
                    otp = "",
                    otpError = null,
                    otpToken = if (result.isSuccess) it.otpToken + 1 else it.otpToken,
                )
            }
        }
    }

    private fun submitPhone() {
        val digits = _state.value.phone.filter(Char::isDigit)
        if (digits.length < MinPhoneDigits) {
            _state.update { it.copy(phoneError = "Enter a valid phone number") }
        } else {
            _state.update {
                it.copy(
                    step = ForgotPasswordStep.PhoneVerification,
                    otp = "",
                    otpError = null,
                    otpToken = it.otpToken + 1,
                )
            }
        }
    }

    private fun submitOtp() {
        if (_state.value.otp != SimulatedOtpCode) {
            _state.update { it.copy(otpError = "Enter the verification code we sent") }
        } else {
            _state.update { it.copy(step = ForgotPasswordStep.NewPassword, otpError = null) }
        }
    }

    private fun resendOtp() {
        _state.update { it.copy(otp = "", otpError = null, otpToken = it.otpToken + 1) }
    }

    private fun submitNewPassword() {
        val result = PasswordValidator.validate(_state.value.newPassword)
        when {
            result is ValidationResult.Invalid -> _state.update { it.copy(passwordError = result.message) }
            _state.value.newPassword != _state.value.confirmPassword -> _state.update {
                it.copy(confirmPasswordError = "Passwords do not match")
            }
            else -> _state.update {
                it.copy(
                    step = ForgotPasswordStep.Success,
                    successMessage = "Password changed successfully, you can login again with a new password",
                )
            }
        }
    }
}
