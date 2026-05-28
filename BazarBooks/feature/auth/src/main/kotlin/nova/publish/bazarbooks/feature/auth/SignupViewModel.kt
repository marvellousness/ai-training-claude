package nova.publish.bazarbooks.feature.auth

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
            SignupIntent.Submit -> submit()
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
                    fullNameError = nameError,
                    emailError = (emailResult as? ValidationResult.Invalid)?.message,
                    passwordError = (passwordResult as? ValidationResult.Invalid)?.message,
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = signUpUseCase(current.fullName.trim(), current.email.trim(), current.password)
            _state.update {
                it.copy(
                    isLoading = false,
                    showSuccess = result.isSuccess,
                    passwordError = result.exceptionOrNull()?.message,
                )
            }
        }
    }

    private fun String.toRequirements() = PasswordRequirements(
        minLength = length >= 8,
        hasNumber = any(Char::isDigit),
        hasLetter = any(Char::isLetter),
    )
}
