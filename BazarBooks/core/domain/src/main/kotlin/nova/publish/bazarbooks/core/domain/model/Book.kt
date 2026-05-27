package nova.publish.bazarbooks.core.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val priceCents: Long,
    val currencyCode: String,
    val imageUrl: String,
    val category: String,
    val rating: Float? = null,
    val description: String? = null,
)
