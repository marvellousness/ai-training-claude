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
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton

@Composable
fun CartScreen(onCheckout: () -> Unit, onBack: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Cart")
        Text("Your selected books are ready for checkout.")
        BazarButton("Checkout", onCheckout)
        BazarSecondaryButton("Back", onBack)
    }
}
