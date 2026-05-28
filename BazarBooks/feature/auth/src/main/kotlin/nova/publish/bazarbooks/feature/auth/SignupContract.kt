package nova.publish.bazarbooks.feature.auth

data class PasswordRequirements(
    val minLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLetter: Boolean = false,
)

data class SignupState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val passwordRequirements: PasswordRequirements = PasswordRequirements(),
    val isLoading: Boolean = false,
    val showSuccess: Boolean = false,
)

sealed interface SignupIntent {
    data class FullNameChanged(val value: String) : SignupIntent
    data class EmailChanged(val value: String) : SignupIntent
    data class PasswordChanged(val value: String) : SignupIntent
    data object Submit : SignupIntent
}
