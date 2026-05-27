package nova.publish.bazarbooks.feature.auth

data class SigninState(val email: String = "", val password: String = "", val errorMessage: String? = null)
sealed interface SigninIntent
sealed interface SigninEffect
