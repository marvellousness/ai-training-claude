package nova.publish.bazarbooks.feature.auth.signup

data class PasswordRequirements(
    val minLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLetter: Boolean = false,
)

enum class SignupStep {
    Form,
    EmailVerification,
    PhoneInput,
    PhoneVerification,
    Success,
}

data class SignupState(
    val step: SignupStep = SignupStep.Form,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val emailOtp: String = "",
    val phone: String = "",
    val phoneOtp: String = "",
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val emailOtpError: String? = null,
    val phoneError: String? = null,
    val phoneOtpError: String? = null,
    val emailOtpToken: Int = 0,
    val phoneOtpToken: Int = 0,
    val passwordRequirements: PasswordRequirements = PasswordRequirements(),
    val isLoading: Boolean = false,
    val showSuccess: Boolean = false,
)

sealed interface SignupIntent {
    data class FullNameChanged(val value: String) : SignupIntent
    data class EmailChanged(val value: String) : SignupIntent
    data class PasswordChanged(val value: String) : SignupIntent
    data class EmailOtpChanged(val value: String) : SignupIntent
    data class PhoneChanged(val value: String) : SignupIntent
    data class PhoneOtpChanged(val value: String) : SignupIntent
    data object Submit : SignupIntent
    data object SubmitEmailOtp : SignupIntent
    data object SubmitPhone : SignupIntent
    data object SubmitPhoneOtp : SignupIntent
    data object ResendEmailOtp : SignupIntent
    data object ResendPhoneOtp : SignupIntent
    data object GetStarted : SignupIntent
}
