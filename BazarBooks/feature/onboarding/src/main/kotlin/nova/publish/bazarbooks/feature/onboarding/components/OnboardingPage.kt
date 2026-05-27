package nova.publish.bazarbooks.feature.onboarding.components

import androidx.annotation.DrawableRes
import nova.publish.bazarbooks.feature.onboarding.R

data class OnboardingPage(
    val title: String,
    val body: String,
    val primaryAction: String,
    @DrawableRes val imageRes: Int,
)

val FigmaOnboardingPages = listOf(
    OnboardingPage(
        title = "Now reading books will be easier",
        body = "Discover new worlds, join a vibrant reading community. Start your reading adventure effortlessly with us.",
        primaryAction = "Continue",
        imageRes = R.drawable.onboarding_books_easier,
    ),
    OnboardingPage(
        title = "Your Bookish Soulmate Awaits",
        body = "Let us be your guide to the perfect read. Discover books tailored to your tastes for a truly rewarding experience.",
        primaryAction = "Get Started",
        imageRes = R.drawable.onboarding_soulmate,
    ),
    OnboardingPage(
        title = "Start Your Adventure",
        body = "Ready to embark on a quest for inspiration and knowledge? Your adventure begins now. Let's go!",
        primaryAction = "Get Started",
        imageRes = R.drawable.onboarding_adventure,
    ),
)
