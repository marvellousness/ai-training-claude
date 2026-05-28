package nova.publish.bazarbooks.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    onCart: () -> Unit,
    onProfile: () -> Unit,
    onNotifications: () -> Unit,
    onBook: (String) -> Unit = {},
    onVendors: () -> Unit = {},
    onAuthors: () -> Unit = {},
    onAuthor: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onLocation: () -> Unit = {},
    onBack: () -> Unit = {},
    initialSurface: HomeSurface = HomeSurface.Home,
    initialBookId: String? = null,
    initialAuthorId: String? = null,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(initialSurface, initialBookId, initialAuthorId) {
        when {
            initialBookId != null -> viewModel.onIntent(HomeIntent.OpenBook(initialBookId))
            initialAuthorId != null -> viewModel.onIntent(HomeIntent.OpenAuthor(initialAuthorId))
            initialSurface == HomeSurface.Category -> viewModel.onIntent(HomeIntent.ShowCategory)
            initialSurface == HomeSurface.Search -> viewModel.onIntent(HomeIntent.ShowSearch)
            initialSurface == HomeSurface.Vendors -> viewModel.onIntent(HomeIntent.ShowVendors)
            initialSurface == HomeSurface.Authors -> viewModel.onIntent(HomeIntent.ShowAuthors)
            initialSurface == HomeSurface.LocationForm -> viewModel.onIntent(HomeIntent.ShowLocationForm)
            else -> viewModel.onIntent(HomeIntent.ShowHome)
        }
    }

    LaunchedEffect(state.effect) {
        if (state.effect == HomeEffect.NavigateCart) {
            onCart()
            viewModel.onIntent(HomeIntent.EffectHandled)
        }
    }

    HomeScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onCart = onCart,
        onProfile = onProfile,
        onNotifications = onNotifications,
        onBook = onBook,
        onVendors = onVendors,
        onAuthors = onAuthors,
        onAuthor = onAuthor,
        onSearch = onSearch,
        onLocation = onLocation,
        onBack = onBack,
    )
}
