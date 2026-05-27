package nova.publish.bazarbooks.feature.onboarding

import nova.publish.bazarbooks.core.common.mvi.BaseMviViewModel

class OnboardingViewModel : BaseMviViewModel<OnboardingState, OnboardingIntent, OnboardingEffect>(OnboardingState()) {
    override fun handleIntent(intent: OnboardingIntent) = Unit
}
