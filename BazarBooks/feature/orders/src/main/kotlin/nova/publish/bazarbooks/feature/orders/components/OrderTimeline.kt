package nova.publish.bazarbooks.feature.orders.components

import androidx.compose.runtime.Composable
import nova.publish.bazarbooks.core.designsystem.component.BazarOrderStatusStepper

@Composable
fun OrderTimeline(steps: List<String>, currentIndex: Int) {
    BazarOrderStatusStepper(steps, currentIndex)
}
