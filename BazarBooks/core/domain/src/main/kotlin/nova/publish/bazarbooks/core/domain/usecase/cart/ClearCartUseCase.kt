package nova.publish.bazarbooks.core.domain.usecase.cart

import nova.publish.bazarbooks.core.domain.repository.CartRepository

class ClearCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke() = repository.clear()
}
