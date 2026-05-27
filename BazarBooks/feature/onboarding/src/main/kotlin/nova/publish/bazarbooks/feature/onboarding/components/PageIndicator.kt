package nova.publish.bazarbooks.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette

@Composable
fun PageIndicator(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        repeat(total) { index ->
            val selected = index == current
            Box(
                modifier = Modifier
                    .size(if (selected) 8.dp else 4.dp)
                    .clip(CircleShape)
                    .background(if (selected) BazarPalette.Primary500 else BazarPalette.Gray200),
            )
        }
    }
}
