package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing

@Composable
fun BazarRatingRow(
    rating: String,
    maxStars: Int = 5,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(maxStars) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = BazarPalette.Yellow)
        }
        Text("($rating)", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun BazarPriceText(
    price: String,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Text(
        text = price,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        fontWeight = FontWeight.Bold,
    )
}
