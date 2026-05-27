package nova.publish.bazarbooks.feature.onboarding

import androidx.compose.runtime.Composable

@Composable
fun OnboardingRoute(onGetStarted: () -> Unit) {
    OnboardingScreen(onGetStarted = onGetStarted)
}
