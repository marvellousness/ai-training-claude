package nova.publish.bazarbooks.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nova.publish.bazarbooks.core.domain.model.Author
import nova.publish.bazarbooks.core.domain.model.Address
import nova.publish.bazarbooks.core.domain.model.Book
import nova.publish.bazarbooks.core.domain.model.Vendor
import nova.publish.bazarbooks.core.domain.repository.AddressRepository
import nova.publish.bazarbooks.core.domain.repository.BookRepository
import nova.publish.bazarbooks.core.domain.repository.CartRepository
import nova.publish.bazarbooks.feature.home.model.AuthorUiModel
import nova.publish.bazarbooks.feature.home.model.BookUiModel
import nova.publish.bazarbooks.feature.home.model.LocationUiModel
import nova.publish.bazarbooks.feature.home.model.VendorUiModel

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val cartRepository: CartRepository,
    private val addressRepository: AddressRepository,
) : ViewModel() {
    constructor(
        bookRepository: BookRepository,
        cartRepository: CartRepository,
        addressRepository: AddressRepository,
        initialSurface: HomeSurface,
        initialBookId: String? = null,
        initialAuthorId: String? = null,
    ) : this(bookRepository, cartRepository, addressRepository) {
        _state.value = _state.value.copy(surface = initialSurface)
        initialBookId?.let { openBook(it) }
        initialAuthorId?.let { openAuthor(it) }
    }

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadCatalog()
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Reload -> loadCatalog()
            HomeIntent.ShowHome -> _state.update { it.copy(surface = HomeSurface.Home) }
            HomeIntent.ShowCategory -> {
                _state.update { it.copy(surface = HomeSurface.Category) }
                searchCategory()
            }
            HomeIntent.ShowSearch -> {
                _state.update { it.copy(surface = HomeSurface.Search) }
                loadRecentSearches()
            }
            HomeIntent.ShowVendors -> _state.update { it.copy(surface = HomeSurface.Vendors) }
            HomeIntent.ShowAuthors -> _state.update { it.copy(surface = HomeSurface.Authors) }
            HomeIntent.ShowLocationForm -> _state.update { it.copy(surface = HomeSurface.LocationForm) }
            is HomeIntent.CategorySelected -> {
                _state.update { it.copy(selectedCategory = intent.category) }
                searchCategory()
            }
            is HomeIntent.VendorTabSelected -> _state.update { it.copy(selectedVendorTab = intent.tab) }
            is HomeIntent.AuthorTabSelected -> _state.update { it.copy(selectedAuthorTab = intent.tab) }
            is HomeIntent.SearchQueryChanged -> _state.update { it.copy(searchQuery = intent.query) }
            HomeIntent.SubmitSearch -> submitSearch()
            is HomeIntent.OpenBook -> openBook(intent.bookId)
            is HomeIntent.OpenAuthor -> openAuthor(intent.authorId)
            HomeIntent.ToggleWishlist -> toggleWishlist()
            HomeIntent.IncreaseQuantity -> _state.update { it.copy(detailQuantity = it.detailQuantity + 1) }
            HomeIntent.DecreaseQuantity -> _state.update { it.copy(detailQuantity = (it.detailQuantity - 1).coerceAtLeast(1)) }
            HomeIntent.AddToCart -> addToCart()
            HomeIntent.ContinueShopping -> _state.update { it.copy(surface = HomeSurface.Home) }
            HomeIntent.ViewCart -> _state.update { it.copy(effect = HomeEffect.NavigateCart) }
            is HomeIntent.LocationFieldChanged -> updateLocationField(intent.field, intent.value)
            HomeIntent.ConfirmLocation -> confirmLocation()
            HomeIntent.EffectHandled -> _state.update { it.copy(effect = null) }
        }
    }

    private fun loadCatalog() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                val books = bookRepository.getHomeBooks().map { it.toUiModel() }
                val vendors = bookRepository.getVendors().map { it.toUiModel() }
                val authors = bookRepository.getAuthors().map { it.toUiModel() }
                val recentSearches = bookRepository.getRecentSearches()
                val selectedLocation = addressRepository.getSelectedAddress()?.toLocationUiModel()
                _state.update {
                    it.copy(
                        isLoading = false,
                        topOfWeek = books,
                        categoryBooks = books,
                        searchResults = books,
                        vendors = vendors,
                        authors = authors,
                        recentSearches = recentSearches,
                        selectedLocation = selectedLocation,
                    )
                }
            }.onFailure { throwable ->
                _state.update { it.copy(isLoading = false, errorMessage = throwable.message ?: "Unable to load catalog") }
            }
        }
    }

    private fun searchCategory() {
        viewModelScope.launch {
            val category = _state.value.selectedCategory.takeUnless { it == "All" }
            val books = bookRepository.searchBooks(query = "", category = category).map { it.toUiModel() }
            _state.update { it.copy(categoryBooks = books) }
        }
    }

    private fun submitSearch() {
        viewModelScope.launch {
            val query = _state.value.searchQuery.trim()
            if (query.isNotBlank()) {
                bookRepository.saveRecentSearch(query)
            }
            val category = _state.value.selectedCategory.takeUnless { it == "All" }
            val results = bookRepository.searchBooks(query = query, category = category).map { it.toUiModel() }
            _state.update {
                it.copy(
                    surface = HomeSurface.Search,
                    searchQuery = query,
                    searchResults = results,
                    recentSearches = bookRepository.getRecentSearches(),
                )
            }
        }
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            _state.update { it.copy(recentSearches = bookRepository.getRecentSearches()) }
        }
    }

    private fun openBook(bookId: String) {
        viewModelScope.launch {
            val book = bookRepository.getBook(bookId)?.toUiModel()
            _state.update {
                it.copy(
                    surface = HomeSurface.BookDetail,
                    selectedBook = book,
                    detailQuantity = 1,
                )
            }
        }
    }

    private fun openAuthor(authorId: String) {
        viewModelScope.launch {
            val author = bookRepository.getAuthor(authorId)?.toUiModel()
            val products = bookRepository.getAuthorBooks(authorId).map { it.toUiModel() }
            _state.update {
                it.copy(
                    surface = HomeSurface.AuthorDetail,
                    selectedAuthor = author,
                    authorProducts = products,
                )
            }
        }
    }

    private fun toggleWishlist() {
        val book = _state.value.selectedBook ?: return
        viewModelScope.launch {
            val isWishlisted = bookRepository.toggleWishlist(book.id)
            _state.update { state ->
                state.copy(
                    selectedBook = state.selectedBook?.copy(isWishlisted = isWishlisted),
                    topOfWeek = state.topOfWeek.replaceBook(book.id) { it.copy(isWishlisted = isWishlisted) },
                    categoryBooks = state.categoryBooks.replaceBook(book.id) { it.copy(isWishlisted = isWishlisted) },
                    searchResults = state.searchResults.replaceBook(book.id) { it.copy(isWishlisted = isWishlisted) },
                )
            }
        }
    }

    private fun addToCart() {
        val bookId = _state.value.selectedBook?.id ?: return
        val quantity = _state.value.detailQuantity
        viewModelScope.launch {
            repeat(quantity) {
                cartRepository.add(bookId)
            }
        }
    }

    private fun updateLocationField(field: String, value: String) {
        _state.update { state ->
            val form = state.locationForm
            state.copy(
                locationForm = when (field) {
                    "name" -> form.copy(name = value)
                    "phone" -> form.copy(phone = value)
                    "governorate" -> form.copy(governorate = value)
                    "city" -> form.copy(city = value)
                    "block" -> form.copy(block = value)
                    "street" -> form.copy(street = value)
                    "building" -> form.copy(building = value)
                    "floor" -> form.copy(floor = value)
                    "flat" -> form.copy(flat = value)
                    "avenue" -> form.copy(avenue = value)
                    else -> form
                },
                locationErrors = state.locationErrors - field,
            )
        }
    }

    private fun confirmLocation() {
        val form = _state.value.locationForm
        val errors = buildMap {
            if (form.name.isBlank()) put("name", "Name is required")
            if (form.phone.filter(Char::isDigit).length < 8) put("phone", "Enter a valid phone number")
            if (form.governorate.isBlank()) put("governorate", "Governorate is required")
            if (form.city.isBlank()) put("city", "City is required")
            if (form.block.isBlank()) put("block", "Block is required")
            if (form.street.isBlank()) put("street", "Street is required")
            if (form.building.isBlank()) put("building", "Building is required")
        }
        _state.update {
            if (errors.isEmpty()) {
                viewModelScope.launch {
                    addressRepository.saveSelectedAddress(form.toDomainAddress())
                }
                it.copy(
                    surface = HomeSurface.LocationDetail,
                    selectedLocation = form,
                    locationErrors = emptyMap(),
                )
            } else {
                it.copy(locationErrors = errors)
            }
        }
    }
}

private fun Book.toUiModel() = BookUiModel(
    id = id,
    title = title,
    author = author,
    price = formatPrice(priceCents, currencyCode),
    imageUrl = imageUrl,
    category = category,
    rating = rating?.toString() ?: "0.0",
    description = description.orEmpty(),
    stock = stock,
    isWishlisted = isWishlisted,
)

private fun Vendor.toUiModel() = VendorUiModel(
    id = id,
    name = name,
    category = category,
    imageUrl = imageUrl,
)

private fun Author.toUiModel() = AuthorUiModel(
    id = id,
    name = name,
    role = role,
    bio = bio,
    imageUrl = imageUrl,
)

private fun List<BookUiModel>.replaceBook(id: String, transform: (BookUiModel) -> BookUiModel) = map { book ->
    if (book.id == id) transform(book) else book
}

private fun formatPrice(priceCents: Long, currencyCode: String): String {
    val major = priceCents / 100
    val minor = priceCents % 100
    val symbol = when (currencyCode) {
        "KWD", "USD" -> "$"
        else -> currencyCode
    }
    return "$symbol$major.${minor.toString().padStart(2, '0')}"
}

private fun LocationUiModel.toDomainAddress() = Address(
    recipient = name,
    phone = phone,
    line1 = line1,
    line2 = listOf(block, floor, flat, avenue).filter { it.isNotBlank() }.joinToString(", ").ifBlank { null },
    city = city,
    region = governorate,
    block = block,
    street = street,
    building = building,
    floor = floor.ifBlank { null },
    flat = flat.ifBlank { null },
    avenue = avenue.ifBlank { null },
)

private fun Address.toLocationUiModel() = LocationUiModel(
    name = recipient,
    phone = phone,
    governorate = region,
    city = city,
    block = block,
    street = street,
    building = building,
    floor = floor.orEmpty(),
    flat = flat.orEmpty(),
    avenue = avenue.orEmpty(),
)
