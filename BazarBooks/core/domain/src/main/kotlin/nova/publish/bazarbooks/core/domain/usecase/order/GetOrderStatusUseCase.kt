package nova.publish.bazarbooks.core.domain.usecase.order

import nova.publish.bazarbooks.core.domain.repository.OrderRepository

class GetOrderStatusUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(orderId: String) = repository.getOrder(orderId)
}
