package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BazarBottomBarItem(
    val label: String,
    val selected: Boolean,
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit,
)

@Composable
fun BazarBottomBar(items: List<BazarBottomBarItem>) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = item.onClick,
                icon = { Icon(item.icon, contentDescription = item.contentDescription) },
                label = { Text(item.label) },
            )
        }
    }
}
