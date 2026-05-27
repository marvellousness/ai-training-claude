package nova.publish.bazarbooks.feature.auth

data class ForgotPasswordState(val email: String = "", val successMessage: String? = null)
