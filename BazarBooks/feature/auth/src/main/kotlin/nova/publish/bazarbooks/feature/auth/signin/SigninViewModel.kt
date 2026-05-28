package nova.publish.bazarbooks.feature.auth.signin

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
import nova.publish.bazarbooks.core.domain.usecase.auth.ContinueAsGuestUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.LoginUseCase

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val continueAsGuestUseCase: ContinueAsGuestUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SigninState())
    val state: StateFlow<SigninState> = _state.asStateFlow()

    fun onIntent(intent: SigninIntent) {
        when (intent) {
            is SigninIntent.EmailChanged -> _state.update { it.copy(email = intent.value, emailError = null, errorMessage = null) }
            is SigninIntent.PasswordChanged -> _state.update { it.copy(password = intent.value, passwordError = null, errorMessage = null) }
            SigninIntent.ContinueAsGuest -> continueAsGuest()
            SigninIntent.NavigationHandled -> _state.update { it.copy(navigateHome = false) }
            SigninIntent.Submit -> submit()
        }
    }

    private fun continueAsGuest() {
        viewModelScope.launch {
            continueAsGuestUseCase()
            _state.update { it.copy(navigateHome = true) }
        }
    }

    private fun submit() {
        val current = _state.value
        val emailResult = EmailValidator.validate(current.email)
        val passwordResult = PasswordValidator.validate(current.password)
        if (emailResult is ValidationResult.Invalid || passwordResult is ValidationResult.Invalid) {
            _state.update {
                it.copy(
                    emailError = (emailResult as? ValidationResult.Invalid)?.message,
                    passwordError = (passwordResult as? ValidationResult.Invalid)?.message,
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = loginUseCase(current.email.trim(), current.password)
            _state.update {
                it.copy(
                    isLoading = false,
                    navigateHome = result.isSuccess,
                    errorMessage = result.exceptionOrNull()?.message,
                )
            }
        }
    }
}
