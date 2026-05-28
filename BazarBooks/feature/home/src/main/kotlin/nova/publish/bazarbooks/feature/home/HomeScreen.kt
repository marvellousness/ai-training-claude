package nova.publish.bazarbooks.feature.home

import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    onCart: () -> Unit,
    onProfile: () -> Unit,
    onNotifications: () -> Unit,
    onBook: (String) -> Unit,
    onVendors: () -> Unit,
    onAuthors: () -> Unit,
    onAuthor: (String) -> Unit,
    onSearch: () -> Unit,
    onLocation: () -> Unit,
    onBack: () -> Unit,
) {
    val navigation = HomeNavigationCallbacks(
        notifications = onNotifications,
        openBook = onBook,
        openVendors = onVendors,
        openAuthors = onAuthors,
        openAuthor = onAuthor,
        search = onSearch,
        back = onBack,
    )

    when (state.surface) {
        HomeSurface.Home -> HomeFeed(state, navigation)
        HomeSurface.Category -> CategoryScreen(state, onIntent, navigation.openBook, navigation.search)
        HomeSurface.Search -> SearchScreen(state, onIntent, navigation.openBook, navigation.back)
        HomeSurface.Vendors -> VendorListScreen(state, onIntent, navigation.back)
        HomeSurface.Authors -> AuthorListScreen(state, onIntent, navigation.openAuthor, navigation.back)
        HomeSurface.AuthorDetail -> AuthorDetailScreen(state, navigation.openBook, navigation.back)
        HomeSurface.BookDetail -> BookDetailScreen(state, onIntent, navigation.back)
        HomeSurface.LocationDetail -> LocationDetailScreen(state, onIntent, navigation.back)
        HomeSurface.LocationForm -> LocationFormScreen(state, onIntent, navigation.back)
    }
}

internal data class HomeNavigationCallbacks(
    val notifications: () -> Unit,
    val openBook: (String) -> Unit,
    val openVendors: () -> Unit,
    val openAuthors: () -> Unit,
    val openAuthor: (String) -> Unit,
    val search: () -> Unit,
    val back: () -> Unit,
)
