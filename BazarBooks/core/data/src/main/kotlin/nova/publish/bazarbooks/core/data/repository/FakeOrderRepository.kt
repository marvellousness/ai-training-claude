package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.domain.model.Address
import nova.publish.bazarbooks.core.domain.model.Order
import nova.publish.bazarbooks.core.domain.model.OrderStatus
import nova.publish.bazarbooks.core.domain.repository.CartRepository
import nova.publish.bazarbooks.core.domain.repository.OrderRepository
import kotlinx.coroutines.flow.first

class FakeOrderRepository(private val cartRepository: CartRepository) : OrderRepository {
    private val orders = mutableListOf<Order>()

    override suspend fun placeOrder(address: Address, paymentMethod: String): Result<Order> {
        val items = cartRepository.observeCart().first()
        if (items.isEmpty()) return Result.failure(IllegalStateException("Cart is empty"))
        val order = Order(
            id = "order-${orders.size + 1}",
            items = items,
            totalCents = items.sumOf { it.book.priceCents * it.quantity },
            currencyCode = items.first().book.currencyCode,
            status = OrderStatus.Confirmed,
            createdAtEpochMillis = System.currentTimeMillis(),
            shippingAddress = address,
        )
        orders += order
        cartRepository.clear()
        return Result.success(order)
    }

    override suspend fun getOrders(): List<Order> = orders.toList()
    override suspend fun getOrder(orderId: String): Order? = orders.firstOrNull { it.id == orderId }
}
