package nova.publish.bazarbooks.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarButton
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Reset password")
        BazarTextField(email, { email = it }, "Email")
        BazarButton("Send reset link", onBack)
        BazarSecondaryButton("Back to sign in", onBack)
    }
}
