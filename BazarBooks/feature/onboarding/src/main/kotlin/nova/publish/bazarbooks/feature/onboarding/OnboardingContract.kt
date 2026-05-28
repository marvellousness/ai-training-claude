package nova.publish.bazarbooks.feature.onboarding

import nova.publish.bazarbooks.core.common.mvi.MviEffect
import nova.publish.bazarbooks.core.common.mvi.MviIntent
import nova.publish.bazarbooks.core.common.mvi.MviState

data class OnboardingState(val pageIndex: Int = 0) : MviState
sealed interface OnboardingIntent : MviIntent {
    data class PageChanged(val index: Int) : OnboardingIntent
    data object GetStartedClicked : OnboardingIntent
}
sealed interface OnboardingEffect : MviEffect { data object NavigateToSignIn : OnboardingEffect }
