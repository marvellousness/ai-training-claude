package nova.publish.bazarbooks.feature.cart_checkout

import androidx.compose.runtime.Composable

@Composable
fun CartRoute(onCheckout: () -> Unit, onBack: () -> Unit) {
    CartScreen(onCheckout = onCheckout, onBack = onBack)
}
