package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun BazarNetworkImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    if (imageUrl.isBlank()) {
        Box(modifier = modifier.clip(MaterialTheme.shapes.medium).background(MaterialTheme.colorScheme.surfaceVariant))
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(MaterialTheme.shapes.medium),
        )
    }
}
