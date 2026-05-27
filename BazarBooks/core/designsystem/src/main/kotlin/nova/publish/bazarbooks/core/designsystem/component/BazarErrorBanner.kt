package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BazarErrorBanner(message: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.errorContainer, shape = MaterialTheme.shapes.medium, modifier = modifier.fillMaxWidth()) {
        Text(message, color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(12.dp))
    }
}
