package nova.publish.bazarbooks.core.domain.repository

import kotlinx.coroutines.flow.Flow
import nova.publish.bazarbooks.core.domain.model.CartItem

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun add(bookId: String)
    suspend fun updateQuantity(bookId: String, quantity: Int)
    suspend fun remove(bookId: String)
    suspend fun clear()
}
