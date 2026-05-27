package nova.publish.bazarbooks.feature.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarButton
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton

@Composable
fun NotificationsScreen(onBack: () -> Unit, onOrder: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Notifications")
        Text("Your order is being prepared.")
        BazarButton("View order", { onOrder("order-1") })
        BazarSecondaryButton("Back", onBack)
    }
}
