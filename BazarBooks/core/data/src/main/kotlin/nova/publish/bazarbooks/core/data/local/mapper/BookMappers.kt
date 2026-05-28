package nova.publish.bazarbooks.core.data.local.mapper

import nova.publish.bazarbooks.core.data.local.entity.BookEntity
import nova.publish.bazarbooks.core.data.local.model.CartItemWithBook
import nova.publish.bazarbooks.core.data.remote.dto.BookDto
import nova.publish.bazarbooks.core.domain.model.Book
import nova.publish.bazarbooks.core.domain.model.CartItem

fun BookDto.toEntity(updatedAtEpochMillis: Long = 0L) = BookEntity(
    id = id,
    title = title,
    author = author,
    vendor = vendor,
    priceCents = priceCents,
    currencyCode = currencyCode,
    imageUrl = imageUrl,
    category = category,
    rating = rating,
    description = description,
    stock = stock,
    discountPercent = discountPercent,
    updatedAtEpochMillis = updatedAtEpochMillis,
)

fun BookEntity.toDomain(isWishlisted: Boolean = false) = Book(
    id = id,
    title = title,
    author = author,
    priceCents = priceCents,
    currencyCode = currencyCode,
    imageUrl = imageUrl,
    category = category,
    rating = rating,
    description = description,
    vendor = vendor,
    stock = stock,
    discountPercent = discountPercent,
    isWishlisted = isWishlisted,
)

fun CartItemWithBook.toDomain() = CartItem(
    book = book.toDomain(),
    quantity = cartItem.quantity,
)
