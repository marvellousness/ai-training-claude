package nova.publish.bazarbooks.feature.auth.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarButton
import nova.publish.bazarbooks.core.designsystem.component.BazarOtpField
import nova.publish.bazarbooks.core.designsystem.component.BazarPasswordField
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onIntent: (ForgotPasswordIntent) -> Unit,
    onBack: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Forgot Password")
        when (state.step) {
            ForgotPasswordStep.Email -> {
                Text("Please enter your email address")
                BazarTextField(state.email, { onIntent(ForgotPasswordIntent.EmailChanged(it)) }, "Email", error = state.emailError)
                BazarButton("Continue", { onIntent(ForgotPasswordIntent.SubmitEmail) }, enabled = !state.isLoading)
            }
            ForgotPasswordStep.Code -> {
                Text("Verification Email")
                Text("Please enter the code we just sent to email ${state.email}")
                BazarOtpField(code = state.code, onCodeChange = { onIntent(ForgotPasswordIntent.CodeChanged(it)) })
                state.codeError?.let { Text(it) }
                BazarButton("Continue", { onIntent(ForgotPasswordIntent.SubmitCode) })
            }
            ForgotPasswordStep.NewPassword -> {
                Text("Create New Password")
                BazarPasswordField(state.newPassword, { onIntent(ForgotPasswordIntent.NewPasswordChanged(it)) }, "Password", error = state.passwordError)
                BazarButton("Continue", { onIntent(ForgotPasswordIntent.SubmitNewPassword) })
            }
            ForgotPasswordStep.Success -> {
                Text("Password Changed")
                Text(state.successMessage.orEmpty())
                BazarButton("Get Started", onBack)
            }
        }
        BazarSecondaryButton("Back to sign in", onBack)
    }
}
