package nova.publish.bazarbooks.feature.home

import androidx.compose.runtime.Composable

@Composable
fun HomeRoute(onCart: () -> Unit, onProfile: () -> Unit, onNotifications: () -> Unit) {
    HomeScreen(onCart = onCart, onProfile = onProfile, onNotifications = onNotifications)
}
