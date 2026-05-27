package nova.publish.bazarbooks.core.domain.repository

import nova.publish.bazarbooks.core.domain.model.Address
import nova.publish.bazarbooks.core.domain.model.Order

interface OrderRepository {
    suspend fun placeOrder(address: Address, paymentMethod: String): Result<Order>
    suspend fun getOrders(): List<Order>
    suspend fun getOrder(orderId: String): Order?
}
