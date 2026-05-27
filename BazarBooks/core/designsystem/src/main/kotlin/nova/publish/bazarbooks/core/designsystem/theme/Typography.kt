package nova.publish.bazarbooks.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val BazarTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
    ),
    displayMedium = TextStyle(
        fontSize = 32.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Bold,
    ),
    displaySmall = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.4.sp,
        fontWeight = FontWeight.Bold,
    ),
    headlineLarge = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
    ),
    headlineMedium = TextStyle(
        fontSize = 18.sp,
        lineHeight = 24.3.sp,
        fontWeight = FontWeight.Bold,
    ),
    headlineSmall = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.3.sp,
    ),
    titleLarge = TextStyle(
        fontSize = 18.sp,
        lineHeight = 27.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.3.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium,
    ),
    titleSmall = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.6.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.6.sp,
        fontWeight = FontWeight.Normal,
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.6.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.3.sp,
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.6.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.2.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.3.sp,
    ),
)

object BazarTextStyles {
    val Heading1 = BazarTypography.displayLarge
    val Heading2 = BazarTypography.displayMedium
    val Heading3 = BazarTypography.displaySmall
    val Heading4 = BazarTypography.headlineLarge
    val Heading5 = BazarTypography.headlineMedium
    val Heading6 = BazarTypography.headlineSmall

    val Body18Medium = BazarTypography.titleLarge
    val Body16Semibold = BazarTypography.bodyLarge
    val Body16Medium = BazarTypography.titleMedium
    val Body16Regular = BazarTypography.titleSmall
    val Body14Bold = BazarTypography.labelLarge
    val Body14Semibold = BazarTypography.bodyMedium
    val Body14Medium = BazarTypography.labelMedium
    val Body14Regular = BazarTypography.bodySmall
    val Body12Bold = BazarTypography.labelSmall
    val Body12Semibold = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.2.sp,
        fontWeight = FontWeight.SemiBold,
    )
    val Body12Medium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.2.sp,
        fontWeight = FontWeight.Medium,
    )
    val Body12Regular = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.2.sp,
        fontWeight = FontWeight.Normal,
    )
}

object BazarTextSpec {
    val SplashLogo = BazarTextStyles.Heading2
    val ScreenTitle = BazarTextStyles.Heading4
    val SectionTitle = BazarTextStyles.Heading5
    val Body = BazarTextStyles.Body14Regular
    val Caption = BazarTextStyles.Body12Regular
    val Button = BazarTextStyles.Body16Semibold
    val FieldLabel = BazarTextStyles.Body14Medium
}
