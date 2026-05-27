package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing

@Composable
fun BazarCheckoutSummary(
    price: String,
    shipping: String,
    total: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(BazarSpacing.Md),
            modifier = Modifier.padding(BazarSpacing.Lg),
        ) {
            Text("Summary", style = MaterialTheme.typography.headlineMedium)
            BazarSummaryRow(label = "Price", value = price)
            BazarSummaryRow(label = "Shipping", value = shipping)
            BazarSummaryRow(label = "Total Payment", value = total, emphasized = true)
        }
    }
}

@Composable
private fun BazarSummaryRow(label: String, value: String, emphasized: Boolean = false) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (emphasized) FontWeight.Bold else FontWeight.Normal,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (emphasized) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
