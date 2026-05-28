package nova.publish.bazarbooks.feature.auth

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue

@Composable
fun SigninRoute(
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
    viewModel: SigninViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateHome) {
        if (state.navigateHome) {
            onSignIn()
            viewModel.onIntent(SigninIntent.NavigationHandled)
        }
    }

    SigninScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onSignUp = onSignUp,
        onForgotPassword = onForgotPassword,
    )
}
