package nova.publish.bazarbooks.core.data.local.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nova.publish.bazarbooks.core.data.local.dao.CartDao
import nova.publish.bazarbooks.core.data.local.entity.CartItemEntity
import nova.publish.bazarbooks.core.data.local.mapper.toDomain
import nova.publish.bazarbooks.core.domain.model.CartItem

class CartLocalDataSource(private val cartDao: CartDao) {
    fun observeCart(): Flow<List<CartItem>> = cartDao.observeCart().map { items ->
        items.map { it.toDomain() }
    }

    suspend fun add(bookId: String, nowMillis: Long) {
        val existing = cartDao.getCartItem(bookId)
        cartDao.upsertCartItem(
            CartItemEntity(
                bookId = bookId,
                quantity = (existing?.quantity ?: 0) + 1,
                updatedAtEpochMillis = nowMillis,
            ),
        )
    }

    suspend fun updateQuantity(bookId: String, quantity: Int, nowMillis: Long) {
        if (quantity <= 0) {
            cartDao.remove(bookId)
        } else {
            cartDao.upsertCartItem(CartItemEntity(bookId = bookId, quantity = quantity, updatedAtEpochMillis = nowMillis))
        }
    }

    suspend fun remove(bookId: String) {
        cartDao.remove(bookId)
    }

    suspend fun clear() {
        cartDao.clear()
    }
}
