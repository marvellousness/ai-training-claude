package nova.publish.bazarbooks.feature.cart_checkout

import androidx.compose.runtime.Composable

@Composable
fun CheckoutRoute(onPlaced: (String) -> Unit, onBack: () -> Unit) {
    CheckoutScreen(onPlaced = { onPlaced("order-1") }, onBack = onBack)
}
