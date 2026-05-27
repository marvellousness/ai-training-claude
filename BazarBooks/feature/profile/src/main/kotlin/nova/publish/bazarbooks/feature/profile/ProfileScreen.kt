package nova.publish.bazarbooks.feature.profile

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
fun ProfileScreen(onOrders: () -> Unit, onNotifications: () -> Unit, onLogout: () -> Unit, onBack: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Bazar Reader")
        Text("reader@example.com")
        BazarButton("Orders", onOrders)
        BazarButton("Notifications", onNotifications)
        BazarSecondaryButton("Logout", onLogout)
        BazarSecondaryButton("Back", onBack)
    }
}
