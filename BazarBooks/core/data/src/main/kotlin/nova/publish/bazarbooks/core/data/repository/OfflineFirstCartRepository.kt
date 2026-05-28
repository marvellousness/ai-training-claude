package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.data.local.source.CartLocalDataSource
import nova.publish.bazarbooks.core.domain.repository.CartRepository

class OfflineFirstCartRepository(
    private val localDataSource: CartLocalDataSource,
) : CartRepository {
    override fun observeCart() = localDataSource.observeCart()

    override suspend fun add(bookId: String) {
        localDataSource.add(bookId, System.currentTimeMillis())
    }

    override suspend fun updateQuantity(bookId: String, quantity: Int) {
        localDataSource.updateQuantity(bookId, quantity, System.currentTimeMillis())
    }

    override suspend fun remove(bookId: String) {
        localDataSource.remove(bookId)
    }

    override suspend fun clear() {
        localDataSource.clear()
    }
}
