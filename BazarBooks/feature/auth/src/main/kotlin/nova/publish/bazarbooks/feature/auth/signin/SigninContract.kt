package nova.publish.bazarbooks.feature.auth.signin

data class SigninState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val navigateHome: Boolean = false,
)

sealed interface SigninIntent {
    data class EmailChanged(val value: String) : SigninIntent
    data class PasswordChanged(val value: String) : SigninIntent
    data object Submit : SigninIntent
    data object ContinueAsGuest : SigninIntent
    data object NavigationHandled : SigninIntent
}
