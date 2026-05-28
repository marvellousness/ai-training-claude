package nova.publish.bazarbooks.core.data.local.model

import androidx.room.Embedded
import nova.publish.bazarbooks.core.data.local.entity.BookEntity
import nova.publish.bazarbooks.core.data.local.entity.CartItemEntity

data class CartItemWithBook(
    @Embedded val cartItem: CartItemEntity,
    @Embedded(prefix = "book_") val book: BookEntity,
)
