package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing

@Composable
fun BazarBookCard(
    title: String,
    author: String,
    price: String,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = Modifier.padding(BazarSpacing.Md)) {
            BazarNetworkImage(
                imageUrl = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxWidth().height(BazarDimensions.BookCoverHeight),
            )
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(author, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(price, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}
