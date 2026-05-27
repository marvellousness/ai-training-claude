package nova.publish.bazarbooks.feature.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarOrderStatusStepper
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton

@Composable
fun OrderStatusScreen(orderId: String, onBack: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Order status")
        Text(orderId)
        BazarOrderStatusStepper(listOf("Placed", "Confirmed", "Packed", "Shipped", "Delivered"), currentIndex = 1)
        BazarSecondaryButton("Back", onBack)
    }
}
