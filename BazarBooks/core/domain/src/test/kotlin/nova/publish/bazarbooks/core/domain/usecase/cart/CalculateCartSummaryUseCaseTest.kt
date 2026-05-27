package nova.publish.bazarbooks.core.domain.usecase.cart

import nova.publish.bazarbooks.core.domain.model.Book
import nova.publish.bazarbooks.core.domain.model.CartItem
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateCartSummaryUseCaseTest {
    private val useCase = CalculateCartSummaryUseCase()

    @Test
    fun `empty cart has zero totals and checkout disabled`() {
        val summary = useCase(emptyList())

        assertEquals(0, summary.subtotalCents)
        assertEquals(0, summary.totalCents)
        assertEquals(false, summary.canCheckout)
    }

    @Test
    fun `summary multiplies item quantity by price`() {
        val book = Book(
            id = "b1",
            title = "Clean Kotlin",
            author = "A Reader",
            priceCents = 1299,
            currencyCode = "USD",
            imageUrl = "",
            category = "Engineering",
        )

        val summary = useCase(listOf(CartItem(book = book, quantity = 2)))

        assertEquals(2598, summary.subtotalCents)
        assertEquals(2598, summary.totalCents)
        assertEquals(true, summary.canCheckout)
    }
}
