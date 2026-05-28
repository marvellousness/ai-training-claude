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
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            is ForgotPasswordIntent.EmailChanged -> _state.update { it.copy(email = intent.value, emailError = null) }
            ForgotPasswordIntent.SubmitEmail -> submitEmail()
            is ForgotPasswordIntent.CodeChanged -> _state.update { it.copy(code = intent.value.filter(Char::isDigit).take(4), codeError = null) }
            ForgotPasswordIntent.SubmitCode -> submitCode()
            is ForgotPasswordIntent.NewPasswordChanged -> _state.update { it.copy(newPassword = intent.value, passwordError = null) }
            ForgotPasswordIntent.SubmitNewPassword -> submitNewPassword()
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
                    step = if (result.isSuccess) ForgotPasswordStep.Code else ForgotPasswordStep.Email,
                    emailError = result.exceptionOrNull()?.message,
                )
            }
        }
    }

    private fun submitCode() {
        if (_state.value.code.length != 4) {
            _state.update { it.copy(codeError = "Enter the 4 digit code") }
        } else {
            _state.update { it.copy(step = ForgotPasswordStep.NewPassword) }
        }
    }

    private fun submitNewPassword() {
        val result = PasswordValidator.validate(_state.value.newPassword)
        if (result is ValidationResult.Invalid) {
            _state.update { it.copy(passwordError = result.message) }
        } else {
            _state.update { it.copy(step = ForgotPasswordStep.Success, successMessage = "Password updated") }
        }
    }
}
