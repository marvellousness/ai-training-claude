package nova.publish.bazarbooks.core.data.remote.dto

data class BookDto(
    val id: String,
    val title: String,
    val author: String,
    val vendor: String,
    val priceCents: Long,
    val currencyCode: String,
    val imageUrl: String,
    val category: String,
    val rating: Float?,
    val description: String?,
    val stock: Int,
    val discountPercent: Int,
)
