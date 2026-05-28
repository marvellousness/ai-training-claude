package nova.publish.bazarbooks.core.designsystem

import nova.publish.bazarbooks.core.designsystem.component.BazarComponentRegistry
import nova.publish.bazarbooks.core.designsystem.theme.BazarDarkColorScheme
import nova.publish.bazarbooks.core.designsystem.theme.BazarLightColorScheme
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DesignSystemPrimitiveContractTest {

    @Test
    fun `component registry covers phase one figma primitives`() {
        val expected = setOf(
            "BazarTopAppBar",
            "BazarBottomBar",
            "BazarPrimaryButton",
            "BazarSecondaryButton",
            "BazarTextField",
            "BazarPasswordField",
            "BazarOtpField",
            "BazarSearchField",
            "BazarAuthTextField",
            "BazarAuthPrimaryButton",
            "BazarSocialSignInButton",
            "BazarDividerLabel",
            "BazarBackButton",
            "BazarInlinePrompt",
            "BazarPasswordRequirementList",
            "BazarSuccessMark",
            "BazarChipTabRow",
            "BazarBookCard",
            "BazarVendorCard",
            "BazarAuthorCard",
            "BazarRatingRow",
            "BazarPriceText",
            "BazarQuantityStepper",
            "BazarCheckoutSummary",
            "BazarEmptyState",
            "BazarConfirmDialog",
        )

        assertEquals(expected, BazarComponentRegistry.phaseOnePrimitiveNames.toSet())
    }

    @Test
    fun `theme exposes distinct light and dark schemes`() {
        assertEquals(BazarPalette.Primary500, BazarLightColorScheme.primary)
        assertNotEquals(BazarLightColorScheme.background, BazarDarkColorScheme.background)
        assertNotEquals(BazarLightColorScheme.surface, BazarDarkColorScheme.surface)
    }
}
