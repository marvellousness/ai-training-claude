package nova.publish.bazarbooks.feature.notifications.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NotificationRow(title: String, body: String) {
    Text("$title: $body")
}
