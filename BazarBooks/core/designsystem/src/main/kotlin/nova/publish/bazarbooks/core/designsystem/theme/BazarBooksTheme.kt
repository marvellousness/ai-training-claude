package nova.publish.bazarbooks.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val BazarLightColorScheme = lightColorScheme(
    primary = BazarPrimary,
    primaryContainer = BazarPrimarySoft,
    onPrimaryContainer = BazarPrimaryDark,
    secondary = BazarSecondary,
    background = BazarBackground,
    surface = BazarSurface,
    surfaceVariant = BazarSurfaceVariant,
    onPrimary = BazarSurface,
    onSecondary = BazarTextPrimary,
    onBackground = BazarTextPrimary,
    onSurface = BazarTextPrimary,
    onSurfaceVariant = BazarTextSecondary,
    outline = BazarDivider,
    error = BazarError,
)

val BazarDarkColorScheme = darkColorScheme(
    primary = BazarPalette.Primary300,
    primaryContainer = BazarPalette.Primary700,
    onPrimary = BazarPalette.Primary900,
    onPrimaryContainer = BazarPalette.Primary50,
    secondary = BazarPalette.Yellow,
    onSecondary = BazarPalette.Gray900,
    background = BazarPalette.Gray900,
    surface = BazarPalette.Gray800,
    surfaceVariant = BazarPalette.Gray700,
    onBackground = BazarPalette.Gray50,
    onSurface = BazarPalette.Gray50,
    onSurfaceVariant = BazarPalette.Gray300,
    outline = BazarPalette.Gray600,
    error = BazarPalette.Red,
)

@Composable
fun BazarBooksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) BazarDarkColorScheme else BazarLightColorScheme,
        typography = BazarTypography,
        shapes = BazarShapes,
        content = content,
    )
}
