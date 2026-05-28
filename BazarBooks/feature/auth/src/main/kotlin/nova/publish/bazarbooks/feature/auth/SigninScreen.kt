package nova.publish.bazarbooks.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarButton
import nova.publish.bazarbooks.core.designsystem.component.BazarPasswordField
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun SigninScreen(
    state: SigninState,
    onIntent: (SigninIntent) -> Unit,
    onSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Welcome Back")
        Text("Sign to your account")
        BazarTextField(
            value = state.email,
            onValueChange = { onIntent(SigninIntent.EmailChanged(it)) },
            label = "Email",
            error = state.emailError,
        )
        BazarPasswordField(
            value = state.password,
            onValueChange = { onIntent(SigninIntent.PasswordChanged(it)) },
            label = "Password",
            error = state.passwordError,
        )
        state.errorMessage?.let { Text(it) }
        BazarButton("Login", { onIntent(SigninIntent.Submit) }, enabled = !state.isLoading)
        BazarSecondaryButton("Continue as guest", { onIntent(SigninIntent.ContinueAsGuest) })
        TextButton(onClick = onForgotPassword) { Text("Forgot password?") }
        TextButton(onClick = onSignUp) { Text("Do not have an account? Sign Up") }
    }
}
