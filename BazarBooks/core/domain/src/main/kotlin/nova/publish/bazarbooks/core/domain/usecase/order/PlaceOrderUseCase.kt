package nova.publish.bazarbooks.core.domain.usecase.order

import nova.publish.bazarbooks.core.domain.model.Address
import nova.publish.bazarbooks.core.domain.repository.OrderRepository

class PlaceOrderUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(address: Address, paymentMethod: String) = repository.placeOrder(address, paymentMethod)
}
