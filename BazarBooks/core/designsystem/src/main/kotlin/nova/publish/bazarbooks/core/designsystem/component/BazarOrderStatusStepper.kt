package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BazarOrderStatusStepper(steps: List<String>, currentIndex: Int, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        steps.forEachIndexed { index, step ->
            Text(
                text = if (index <= currentIndex) "[x] $step" else "[ ] $step",
                color = if (index <= currentIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (index == currentIndex) FontWeight.SemiBold else FontWeight.Normal,
            )
        }
    }
}
