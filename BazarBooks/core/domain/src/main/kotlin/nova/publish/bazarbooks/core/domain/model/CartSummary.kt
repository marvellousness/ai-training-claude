package nova.publish.bazarbooks.core.domain.model

data class CartSummary(
    val items: List<CartItem>,
    val subtotalCents: Long,
    val shippingCents: Long = 0,
    val discountCents: Long = 0,
    val totalCents: Long,
    val currencyCode: String = "USD",
    val canCheckout: Boolean,
)
