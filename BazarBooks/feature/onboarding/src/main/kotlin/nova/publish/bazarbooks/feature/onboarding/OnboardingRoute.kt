package nova.publish.bazarbooks.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OnboardingRoute(
    onGetStarted: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnboardingEffect.NavigateToSignIn -> onGetStarted()
            }
        }
    }

    OnboardingScreen(
        pageIndex = state.pageIndex,
        onPageChanged = { index -> viewModel.onIntent(OnboardingIntent.PageChanged(index)) },
    ) { viewModel.onIntent(OnboardingIntent.GetStartedClicked) }
}
