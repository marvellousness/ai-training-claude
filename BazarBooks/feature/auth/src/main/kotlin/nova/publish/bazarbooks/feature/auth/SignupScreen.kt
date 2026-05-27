package nova.publish.bazarbooks.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarButton
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun SignupScreen(onSignUp: () -> Unit, onSignIn: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Create account")
        BazarTextField(name, { name = it }, "Full name")
        BazarTextField(email, { email = it }, "Email")
        BazarTextField(password, { password = it }, "Password")
        BazarButton("Sign up", onSignUp)
        TextButton(onClick = onSignIn) { Text("Already have an account?") }
    }
}
