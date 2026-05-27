package nova.publish.bazarbooks.core.domain.model

data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalCents: Long,
    val currencyCode: String,
    val status: OrderStatus,
    val createdAtEpochMillis: Long,
    val shippingAddress: Address,
)

enum class OrderStatus {
    Placed,
    Confirmed,
    Packed,
    Shipped,
    Delivered,
    Cancelled,
}
