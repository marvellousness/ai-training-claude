package nova.publish.bazarbooks.feature.notifications

import androidx.compose.runtime.Composable

@Composable
fun NotificationsRoute(onBack: () -> Unit, onOrder: (String) -> Unit) {
    NotificationsScreen(onBack = onBack, onOrder = onOrder)
}
