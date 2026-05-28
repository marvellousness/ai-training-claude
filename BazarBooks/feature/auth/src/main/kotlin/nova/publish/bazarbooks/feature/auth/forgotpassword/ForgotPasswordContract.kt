package nova.publish.bazarbooks.feature.auth.forgotpassword

enum class ForgotPasswordStep {
    Email,
    Code,
    NewPassword,
    Success,
}

data class ForgotPasswordState(
    val email: String = "",
    val code: String = "",
    val newPassword: String = "",
    val emailError: String? = null,
    val codeError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val step: ForgotPasswordStep = ForgotPasswordStep.Email,
    val successMessage: String? = null,
)

sealed interface ForgotPasswordIntent {
    data class EmailChanged(val value: String) : ForgotPasswordIntent
    data object SubmitEmail : ForgotPasswordIntent
    data class CodeChanged(val value: String) : ForgotPasswordIntent
    data object SubmitCode : ForgotPasswordIntent
    data class NewPasswordChanged(val value: String) : ForgotPasswordIntent
    data object SubmitNewPassword : ForgotPasswordIntent
}
