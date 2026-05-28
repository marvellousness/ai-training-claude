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
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun SignupScreen(
    state: SignupState,
    onIntent: (SignupIntent) -> Unit,
    onSignIn: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Sign Up")
        Text("Create account and choose favorite menu")
        BazarTextField(state.fullName, { onIntent(SignupIntent.FullNameChanged(it)) }, "Name", error = state.fullNameError)
        BazarTextField(state.email, { onIntent(SignupIntent.EmailChanged(it)) }, "Email", error = state.emailError)
        BazarPasswordField(state.password, { onIntent(SignupIntent.PasswordChanged(it)) }, "Password", error = state.passwordError)
        Text(if (state.passwordRequirements.minLength) "Minimum 8 characters" else "Minimum 8 characters required")
        Text(if (state.passwordRequirements.hasNumber) "At least 1 number" else "At least 1 number required")
        Text(if (state.passwordRequirements.hasLetter) "At least 1 letter" else "At least 1 letter required")
        Text("By clicking Register, you agree to our Terms and Data Policy.")
        BazarButton("Register", { onIntent(SignupIntent.Submit) }, enabled = !state.isLoading)
        TextButton(onClick = onSignIn) { Text("Have an account? Sign In") }
    }
}
