package nova.publish.bazarbooks.feature.home

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nova.publish.bazarbooks.core.domain.model.Author
import nova.publish.bazarbooks.core.domain.model.Address
import nova.publish.bazarbooks.core.domain.model.Book
import nova.publish.bazarbooks.core.domain.model.CartItem
import nova.publish.bazarbooks.core.domain.model.Vendor
import nova.publish.bazarbooks.core.domain.repository.AddressRepository
import nova.publish.bazarbooks.core.domain.repository.BookRepository
import nova.publish.bazarbooks.core.domain.repository.CartRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var bookRepository: RecordingBookRepository
    private lateinit var cartRepository: RecordingCartRepository
    private lateinit var addressRepository: RecordingAddressRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        bookRepository = RecordingBookRepository()
        cartRepository = RecordingCartRepository()
        addressRepository = RecordingAddressRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `home loads figma sections for offers top books vendors and authors`() = runTest(dispatcher) {
        val viewModel = viewModel(HomeSurface.Home)
        advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(HomeSurface.Home, state.surface)
        assertEquals("Special Offer", state.offer.title)
        assertEquals("Discount 25%", state.offer.subtitle)
        assertEquals("Order Now", state.offer.actionLabel)
        assertEquals(listOf("Top of Week", "Best Vendors", "Authors"), state.homeSectionTitles)
        assertEquals("Wattpad", state.vendors.first().name)
        assertEquals("John Freeman", state.authors.first().name)
    }

    @Test
    fun `category selects figma tab and searches with persisted recent term`() = runTest(dispatcher) {
        val viewModel = viewModel(HomeSurface.Category)
        advanceUntilIdle()

        viewModel.onIntent(HomeIntent.CategorySelected("Romantic"))
        viewModel.onIntent(HomeIntent.SearchQueryChanged("The Good Sister"))
        viewModel.onIntent(HomeIntent.SubmitSearch)
        advanceUntilIdle()

        assertEquals(HomeSurface.Search, viewModel.state.value.surface)
        assertEquals("Romantic", bookRepository.lastCategory)
        assertEquals("The Good Sister", bookRepository.savedSearches.single())
        assertTrue(viewModel.state.value.recentSearches.contains("The Good Sister"))
    }

    @Test
    fun `book detail updates quantity adds to cart and requests cart navigation`() = runTest(dispatcher) {
        val viewModel = viewModel(HomeSurface.BookDetail, initialBookId = "kite-runner")
        advanceUntilIdle()

        viewModel.onIntent(HomeIntent.IncreaseQuantity)
        viewModel.onIntent(HomeIntent.AddToCart)
        viewModel.onIntent(HomeIntent.ViewCart)
        advanceUntilIdle()

        assertEquals(2, viewModel.state.value.detailQuantity)
        assertEquals(listOf("kite-runner", "kite-runner"), cartRepository.addedBookIds)
        assertEquals(HomeEffect.NavigateCart, viewModel.state.value.effect)
    }

    @Test
    fun `location validates required fields before persisting address`() = runTest(dispatcher) {
        val viewModel = viewModel(HomeSurface.LocationForm)
        advanceUntilIdle()

        viewModel.onIntent(HomeIntent.ConfirmLocation)

        assertEquals("Name is required", viewModel.state.value.locationErrors["name"])

        viewModel.onIntent(HomeIntent.LocationFieldChanged("name", "Bazar Reader"))
        viewModel.onIntent(HomeIntent.LocationFieldChanged("phone", "+9651234357565"))
        viewModel.onIntent(HomeIntent.LocationFieldChanged("governorate", "Kuwait City"))
        viewModel.onIntent(HomeIntent.LocationFieldChanged("city", "Dumbo"))
        viewModel.onIntent(HomeIntent.LocationFieldChanged("block", "20"))
        viewModel.onIntent(HomeIntent.LocationFieldChanged("street", "Utama Street"))
        viewModel.onIntent(HomeIntent.LocationFieldChanged("building", "No.20"))
        viewModel.onIntent(HomeIntent.ConfirmLocation)
        advanceUntilIdle()

        assertEquals(HomeSurface.LocationDetail, viewModel.state.value.surface)
        assertEquals("Utama Street, No.20", viewModel.state.value.selectedLocation?.line1)
        assertEquals("Bazar Reader", addressRepository.savedAddress?.recipient)
    }

    private fun viewModel(
        initialSurface: HomeSurface,
        initialBookId: String? = null,
        initialAuthorId: String? = null,
    ) = HomeViewModel(
        bookRepository = bookRepository,
        cartRepository = cartRepository,
        addressRepository = addressRepository,
        initialSurface = initialSurface,
        initialBookId = initialBookId,
        initialAuthorId = initialAuthorId,
    )

    private class RecordingBookRepository : BookRepository {
        val savedSearches = mutableListOf<String>()
        var lastCategory: String? = null

        private val books = listOf(
            Book("kite-runner", "The Kite Runner", "Khaled Hosseini", 3999, "KWD", "", "Novels", 4.0f, "A moving Figma detail-screen sample.", "Bazar", 12, 25),
            Book("good-sister", "The Good Sister", "Sally Hepworth", 2712, "KWD", "", "Romantic", 4.1f, "A tense relationship story.", "Haymarket", 7, 10),
        )

        override suspend fun getHomeBooks(): List<Book> = books
        override suspend fun searchBooks(query: String, category: String?): List<Book> {
            lastCategory = category
            return books.filter { book ->
                (query.isBlank() || book.title.contains(query, ignoreCase = true)) &&
                    (category == null || category == "All" || book.category == category)
            }
        }

        override suspend fun getBook(id: String): Book? = books.firstOrNull { it.id == id }
        override suspend fun getVendors(): List<Vendor> = listOf(Vendor("wattpad", "Wattpad", "Books", ""))
        override suspend fun getAuthors(): List<Author> = listOf(Author("john-freeman", "John Freeman", "Writer", "American writer he was the editor of the", ""))
        override suspend fun getAuthor(id: String): Author? = getAuthors().firstOrNull { it.id == id }
        override suspend fun getAuthorBooks(authorId: String): List<Book> = books
        override suspend fun toggleWishlist(bookId: String): Boolean = true
        override suspend fun getRecentSearches(): List<String> = savedSearches
        override suspend fun saveRecentSearch(query: String) {
            savedSearches += query
        }
    }

    private class RecordingCartRepository : CartRepository {
        val addedBookIds = mutableListOf<String>()
        override fun observeCart(): Flow<List<CartItem>> = emptyFlow()
        override suspend fun add(bookId: String) {
            addedBookIds += bookId
        }

        override suspend fun updateQuantity(bookId: String, quantity: Int) = Unit
        override suspend fun remove(bookId: String) = Unit
        override suspend fun clear() = Unit
    }

    private class RecordingAddressRepository : AddressRepository {
        var savedAddress: Address? = null
        override suspend fun getSelectedAddress(): Address? = savedAddress
        override suspend fun saveSelectedAddress(address: Address): Address {
            savedAddress = address
            return address
        }
    }
}
