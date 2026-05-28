package nova.publish.bazarbooks.feature.home

import nova.publish.bazarbooks.feature.home.model.AuthorUiModel
import nova.publish.bazarbooks.feature.home.model.BookUiModel
import nova.publish.bazarbooks.feature.home.model.LocationUiModel
import nova.publish.bazarbooks.feature.home.model.OfferUiModel
import nova.publish.bazarbooks.feature.home.model.VendorUiModel

enum class HomeSurface {
    Home,
    Category,
    Search,
    Vendors,
    Authors,
    AuthorDetail,
    BookDetail,
    LocationDetail,
    LocationForm,
}

data class HomeState(
    val surface: HomeSurface = HomeSurface.Home,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val offer: OfferUiModel = OfferUiModel(),
    val topOfWeek: List<BookUiModel> = emptyList(),
    val categoryBooks: List<BookUiModel> = emptyList(),
    val searchResults: List<BookUiModel> = emptyList(),
    val vendors: List<VendorUiModel> = emptyList(),
    val authors: List<AuthorUiModel> = emptyList(),
    val authorProducts: List<BookUiModel> = emptyList(),
    val selectedBook: BookUiModel? = null,
    val selectedAuthor: AuthorUiModel? = null,
    val selectedCategory: String = FigmaCategoryTabs.first(),
    val selectedVendorTab: String = FigmaVendorTabs.first(),
    val selectedAuthorTab: String = FigmaAuthorTabs.first(),
    val searchQuery: String = "",
    val recentSearches: List<String> = emptyList(),
    val detailQuantity: Int = 1,
    val selectedLocation: LocationUiModel? = null,
    val locationForm: LocationUiModel = LocationUiModel(),
    val locationErrors: Map<String, String> = emptyMap(),
    val effect: HomeEffect? = null,
) {
    val homeSectionTitles: List<String>
        get() = listOf("Top of Week", "Best Vendors", "Authors")
}

sealed interface HomeIntent {
    data object Reload : HomeIntent
    data object ShowHome : HomeIntent
    data object ShowCategory : HomeIntent
    data object ShowSearch : HomeIntent
    data object ShowVendors : HomeIntent
    data object ShowAuthors : HomeIntent
    data object ShowLocationForm : HomeIntent
    data class CategorySelected(val category: String) : HomeIntent
    data class VendorTabSelected(val tab: String) : HomeIntent
    data class AuthorTabSelected(val tab: String) : HomeIntent
    data class SearchQueryChanged(val query: String) : HomeIntent
    data object SubmitSearch : HomeIntent
    data class OpenBook(val bookId: String) : HomeIntent
    data class OpenAuthor(val authorId: String) : HomeIntent
    data object ToggleWishlist : HomeIntent
    data object IncreaseQuantity : HomeIntent
    data object DecreaseQuantity : HomeIntent
    data object AddToCart : HomeIntent
    data object ContinueShopping : HomeIntent
    data object ViewCart : HomeIntent
    data class LocationFieldChanged(val field: String, val value: String) : HomeIntent
    data object ConfirmLocation : HomeIntent
    data object EffectHandled : HomeIntent
}

sealed interface HomeEffect {
    data object NavigateCart : HomeEffect
}

val FigmaCategoryTabs = listOf("All", "Novels", "Self Love", "Science", "Romantic")
val FigmaVendorTabs = listOf("All", "Books", "Poems", "Special for you", "Stationary", "Our Vendors")
val FigmaAuthorTabs = listOf("All", "Poets", "Playwrights", "Novelists", "Journalists")
