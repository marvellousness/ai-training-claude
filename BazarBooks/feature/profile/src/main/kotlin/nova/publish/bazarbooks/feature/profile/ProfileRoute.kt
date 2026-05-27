package nova.publish.bazarbooks.feature.profile

import androidx.compose.runtime.Composable

@Composable
fun ProfileRoute(onOrders: () -> Unit, onNotifications: () -> Unit, onLogout: () -> Unit, onBack: () -> Unit) {
    ProfileScreen(onOrders = onOrders, onNotifications = onNotifications, onLogout = onLogout, onBack = onBack)
}
