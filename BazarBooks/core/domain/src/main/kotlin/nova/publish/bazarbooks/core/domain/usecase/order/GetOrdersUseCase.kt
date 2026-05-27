package nova.publish.bazarbooks.core.domain.usecase.order

import nova.publish.bazarbooks.core.domain.repository.OrderRepository

class GetOrdersUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke() = repository.getOrders()
}
