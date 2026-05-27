package nova.publish.bazarbooks.feature.onboarding

import nova.publish.bazarbooks.feature.onboarding.components.FigmaOnboardingPages
import org.junit.Assert.assertEquals
import org.junit.Test

class OnboardingContentTest {
    @Test
    fun figmaOnboardingPagesMatchExportedCopyAndActions() {
        assertEquals(3, FigmaOnboardingPages.size)

        assertEquals("Now reading books will be easier", FigmaOnboardingPages[0].title)
        assertEquals(
            "Discover new worlds, join a vibrant reading community. Start your reading adventure effortlessly with us.",
            FigmaOnboardingPages[0].body,
        )
        assertEquals("Continue", FigmaOnboardingPages[0].primaryAction)

        assertEquals("Your Bookish Soulmate Awaits", FigmaOnboardingPages[1].title)
        assertEquals(
            "Let us be your guide to the perfect read. Discover books tailored to your tastes for a truly rewarding experience.",
            FigmaOnboardingPages[1].body,
        )
        assertEquals("Get Started", FigmaOnboardingPages[1].primaryAction)

        assertEquals("Start Your Adventure", FigmaOnboardingPages[2].title)
        assertEquals(
            "Ready to embark on a quest for inspiration and knowledge? Your adventure begins now. Let's go!",
            FigmaOnboardingPages[2].body,
        )
        assertEquals("Get Started", FigmaOnboardingPages[2].primaryAction)
    }
}
