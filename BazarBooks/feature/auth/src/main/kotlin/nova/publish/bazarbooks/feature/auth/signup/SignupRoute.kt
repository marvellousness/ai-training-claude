package nova.publish.bazarbooks.feature.auth.signup

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignupRoute(
    onSignUp: () -> Unit,
    onSignIn: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.showSuccess) {
        if (state.showSuccess) {
            onSignUp()
        }
    }

    SignupScreen(state = state, onIntent = viewModel::onIntent, onSignIn = onSignIn)
}
