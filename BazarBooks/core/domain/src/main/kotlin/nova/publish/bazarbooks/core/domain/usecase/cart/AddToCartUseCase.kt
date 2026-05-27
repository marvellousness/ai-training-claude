package nova.publish.bazarbooks.core.domain.usecase.cart

import nova.publish.bazarbooks.core.domain.repository.CartRepository

class AddToCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(bookId: String) = repository.add(bookId)
}
