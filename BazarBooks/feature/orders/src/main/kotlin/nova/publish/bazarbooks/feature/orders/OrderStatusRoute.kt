package nova.publish.bazarbooks.feature.orders

import androidx.compose.runtime.Composable

@Composable
fun OrderStatusRoute(orderId: String, onBack: () -> Unit) {
    OrderStatusScreen(orderId = orderId, onBack = onBack)
}
