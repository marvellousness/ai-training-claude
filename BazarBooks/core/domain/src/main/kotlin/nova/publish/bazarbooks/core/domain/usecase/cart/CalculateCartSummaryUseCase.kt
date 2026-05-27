package nova.publish.bazarbooks.core.domain.usecase.cart

import nova.publish.bazarbooks.core.domain.model.CartItem
import nova.publish.bazarbooks.core.domain.model.CartSummary

class CalculateCartSummaryUseCase {
    operator fun invoke(items: List<CartItem>): CartSummary {
        val subtotal = items.sumOf { it.book.priceCents * it.quantity }
        return CartSummary(
            items = items,
            subtotalCents = subtotal,
            totalCents = subtotal,
            currencyCode = items.firstOrNull()?.book?.currencyCode ?: "USD",
            canCheckout = items.isNotEmpty(),
        )
    }
}
