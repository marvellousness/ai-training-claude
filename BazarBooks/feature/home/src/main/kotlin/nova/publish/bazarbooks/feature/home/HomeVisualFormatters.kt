package nova.publish.bazarbooks.feature.home

import androidx.compose.ui.graphics.Color
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette

internal fun vendorLogoText(name: String): String = when (name) {
    "Warehouse" -> "WS\nwarehouse"
    "Kuromi" -> "Kuromi"
    "GooDay" -> "GooDay"
    "Crane & Co" -> "CRANE & CO."
    else -> name
}

internal fun vendorLogoColor(name: String): Color = when (name) {
    "Kuromi" -> Color(0xFFE91E63)
    "GooDay" -> Color(0xFFFF6D2D)
    "Crane & Co" -> BazarPalette.Gray600
    else -> BazarPalette.Gray900
}

internal fun authorColor(id: String): Color = when (id) {
    "john-freeman" -> Color(0xFF6E4A37)
    "tess-gunty" -> Color(0xFF2F5F94)
    "richard-perston" -> BazarPalette.Gray600
    "adam-dalva" -> Color(0xFF374151)
    else -> BazarPalette.Primary400
}
