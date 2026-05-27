package nova.publish.bazarbooks.core.domain.usecase.cart

import nova.publish.bazarbooks.core.domain.repository.CartRepository

class ObserveCartUseCase(private val repository: CartRepository) {
    operator fun invoke() = repository.observeCart()
}
