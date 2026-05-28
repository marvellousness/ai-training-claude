package nova.publish.bazarbooks.feature.auth.forgotpassword

enum class ForgotPasswordStep {
    MethodSelection,
    EmailInput,
    PhoneInput,
    EmailVerification,
    PhoneVerification,
    NewPassword,
    Success,
}

enum class ForgotPasswordMethod {
    Email,
    Phone,
}

data class ForgotPasswordState(
    val selectedMethod: ForgotPasswordMethod? = null,
    val email: String = "",
    val phone: String = "",
    val otp: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val methodError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val otpError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val step: ForgotPasswordStep = ForgotPasswordStep.MethodSelection,
    val otpToken: Int = 0,
    val navigateSignIn: Boolean = false,
    val successMessage: String? = null,
)

sealed interface ForgotPasswordIntent {
    data class MethodSelected(val value: ForgotPasswordMethod) : ForgotPasswordIntent
    data object SubmitMethod : ForgotPasswordIntent
    data class EmailChanged(val value: String) : ForgotPasswordIntent
    data object SubmitEmail : ForgotPasswordIntent
    data class PhoneChanged(val value: String) : ForgotPasswordIntent
    data object SubmitPhone : ForgotPasswordIntent
    data class OtpChanged(val value: String) : ForgotPasswordIntent
    data object SubmitOtp : ForgotPasswordIntent
    data object ResendOtp : ForgotPasswordIntent
    data class NewPasswordChanged(val value: String) : ForgotPasswordIntent
    data class ConfirmPasswordChanged(val value: String) : ForgotPasswordIntent
    data object SubmitNewPassword : ForgotPasswordIntent
    data object Login : ForgotPasswordIntent
    data object NavigationHandled : ForgotPasswordIntent
}
