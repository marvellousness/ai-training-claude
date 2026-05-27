package nova.publish.bazarbooks.core.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nova.publish.bazarbooks.core.data.seed.SeedBooks
import nova.publish.bazarbooks.core.domain.model.CartItem
import nova.publish.bazarbooks.core.domain.repository.CartRepository

class FakeCartRepository : CartRepository {
    private val cart = MutableStateFlow<List<CartItem>>(emptyList())

    override fun observeCart() = cart.asStateFlow()

    override suspend fun add(bookId: String) {
        val book = SeedBooks.books.firstOrNull { it.id == bookId } ?: return
        val existing = cart.value.firstOrNull { it.book.id == bookId }
        cart.value = if (existing == null) {
            cart.value + CartItem(book, 1)
        } else {
            cart.value.map { if (it.book.id == bookId) it.copy(quantity = it.quantity + 1) else it }
        }
    }

    override suspend fun updateQuantity(bookId: String, quantity: Int) {
        cart.value = if (quantity <= 0) {
            cart.value.filterNot { it.book.id == bookId }
        } else {
            cart.value.map { if (it.book.id == bookId) it.copy(quantity = quantity) else it }
        }
    }

    override suspend fun remove(bookId: String) {
        cart.value = cart.value.filterNot { it.book.id == bookId }
    }

    override suspend fun clear() {
        cart.value = emptyList()
    }
}
