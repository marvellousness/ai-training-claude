package nova.publish.bazarbooks.feature.auth.components

import androidx.compose.runtime.Composable
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    BazarTextField(value = value, onValueChange = onValueChange, label = "Password")
}
