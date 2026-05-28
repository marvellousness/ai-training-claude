package nova.publish.bazarbooks.feature.onboarding

import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.feature.onboarding.components.OnboardingPages
import org.junit.Assert.assertEquals
import org.junit.Test

class OnboardingContentTest {
    @Test
    fun onboardingPagesMatchApprovedCopyAndActions() {
        assertEquals(3, OnboardingPages.size)

        assertEquals("Now reading books will be easier", OnboardingPages[0].title)
        assertEquals(
            "Discover new worlds, join a vibrant reading community. Start your reading adventure effortlessly with us.",
            OnboardingPages[0].body,
        )
        assertEquals("Continue", OnboardingPages[0].primaryAction)

        assertEquals("Your Bookish Soulmate Awaits", OnboardingPages[1].title)
        assertEquals(
            "Let us be your guide to the perfect read. Discover books tailored to your tastes for a truly rewarding experience.",
            OnboardingPages[1].body,
        )
        assertEquals("Get Started", OnboardingPages[1].primaryAction)

        assertEquals("Start Your Adventure", OnboardingPages[2].title)
        assertEquals(
            "Ready to embark on a quest for inspiration and knowledge? Your adventure begins now. Let's go!",
            OnboardingPages[2].body,
        )
        assertEquals("Get Started", OnboardingPages[2].primaryAction)
    }
}
