package nova.publish.bazarbooks.feature.auth

import androidx.compose.runtime.Composable

@Composable
fun SigninRoute(onSignIn: () -> Unit, onSignUp: () -> Unit, onForgotPassword: () -> Unit) {
    SigninScreen(onSignIn = onSignIn, onSignUp = onSignUp, onForgotPassword = onForgotPassword)
}
