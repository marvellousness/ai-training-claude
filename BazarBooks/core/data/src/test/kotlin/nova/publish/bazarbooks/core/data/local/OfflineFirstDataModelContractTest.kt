package nova.publish.bazarbooks.core.data.local

import nova.publish.bazarbooks.core.data.local.entity.BookEntity
import nova.publish.bazarbooks.core.data.local.entity.CartItemEntity
import nova.publish.bazarbooks.core.data.local.mapper.toDomain
import nova.publish.bazarbooks.core.data.local.mapper.toEntity
import nova.publish.bazarbooks.core.data.local.model.CartItemWithBook
import nova.publish.bazarbooks.core.data.remote.dto.BookDto
import nova.publish.bazarbooks.core.data.repository.model.BookSearchRequest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OfflineFirstDataModelContractTest {

    @Test
    fun `database schema covers figma offline first entities`() {
        val expected = setOf(
            "books",
            "categories",
            "authors",
            "vendors",
            "book_categories",
            "related_books",
            "reviews",
            "wishlist",
            "cart_items",
            "addresses",
            "payment_methods",
            "orders",
            "order_items",
            "order_status_history",
            "notifications",
            "offers",
            "profiles",
            "recent_searches",
            "pending_operations",
        )

        assertEquals(expected, BazarBooksSchema.tableNames.toSet())
    }

    @Test
    fun `book dto maps through room entity into domain book`() {
        val dto = BookDto(
            id = "kite-runner",
            title = "The Kite Runner",
            author = "Khaled Hosseini",
            vendor = "Bazar",
            priceCents = 3999,
            currencyCode = "KWD",
            imageUrl = "cover.png",
            category = "Novels",
            rating = 4.0f,
            description = "A Figma detail-screen sample.",
            stock = 12,
            discountPercent = 25,
        )

        val entity = dto.toEntity()
        val domain = entity.toDomain(isWishlisted = true)

        assertEquals("kite-runner", entity.id)
        assertEquals("The Kite Runner", domain.title)
        assertEquals("Khaled Hosseini", domain.author)
        assertEquals(3999, domain.priceCents)
        assertEquals("KWD", domain.currencyCode)
        assertEquals("Novels", domain.category)
        assertEquals(4.0f, domain.rating)
    }

    @Test
    fun `cart item with joined book maps to domain cart item`() {
        val joined = CartItemWithBook(
            cartItem = CartItemEntity(bookId = "book-1", quantity = 3, updatedAtEpochMillis = 10L),
            book = BookEntity(
                id = "book-1",
                title = "Carrie Fisher",
                author = "Carrie Fisher",
                vendor = "Bazar",
                priceCents = 2712,
                currencyCode = "KWD",
                imageUrl = "",
                category = "Novels",
                rating = 4.0f,
                description = "Sample",
                stock = 5,
                discountPercent = 0,
                updatedAtEpochMillis = 10L,
            ),
        )

        val item = joined.toDomain()

        assertEquals("book-1", item.book.id)
        assertEquals(3, item.quantity)
        assertEquals(2712, item.book.priceCents)
    }

    @Test
    fun `search request carries server style filters for local room execution`() {
        val request = BookSearchRequest(
            query = "runner",
            category = "Novels",
            author = "Khaled Hosseini",
            minPriceCents = 1000,
            maxPriceCents = 5000,
            minRating = 4.0f,
            sort = BookSearchRequest.Sort.RatingDesc,
            page = 2,
            pageSize = 20,
        )

        assertTrue(request.hasFilters)
        assertEquals(20, request.offset)
    }
}
