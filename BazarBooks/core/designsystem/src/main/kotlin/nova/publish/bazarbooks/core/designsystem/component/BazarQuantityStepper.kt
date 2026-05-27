package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun BazarQuantityStepper(quantity: Int, onDecrease: () -> Unit, onIncrease: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDecrease) { Icon(Icons.Filled.Remove, contentDescription = "Decrease quantity") }
        Text(quantity.toString())
        IconButton(onClick = onIncrease) { Icon(Icons.Filled.Add, contentDescription = "Increase quantity") }
    }
}
