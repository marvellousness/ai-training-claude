package nova.publish.bazarbooks.feature.auth

import androidx.compose.runtime.Composable

@Composable
fun SignupRoute(onSignUp: () -> Unit, onSignIn: () -> Unit) {
    SignupScreen(onSignUp = onSignUp, onSignIn = onSignIn)
}
