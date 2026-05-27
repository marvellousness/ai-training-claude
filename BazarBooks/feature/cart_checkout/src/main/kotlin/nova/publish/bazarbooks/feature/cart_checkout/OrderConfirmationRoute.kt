package nova.publish.bazarbooks.feature.cart_checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarButton

@Composable
fun OrderConfirmationRoute(orderId: String, onDone: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Order confirmed")
        Text(orderId)
        BazarButton("Done", onDone)
    }
}
