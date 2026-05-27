package nova.publish.bazarbooks.core.domain.usecase.cart

import nova.publish.bazarbooks.core.domain.repository.CartRepository

class UpdateCartQuantityUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(bookId: String, quantity: Int) = repository.updateQuantity(bookId, quantity.coerceAtLeast(0))
}
