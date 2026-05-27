package nova.publish.bazarbooks.core.domain.usecase.cart

import nova.publish.bazarbooks.core.domain.repository.CartRepository

class RemoveFromCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(bookId: String) = repository.remove(bookId)
}
