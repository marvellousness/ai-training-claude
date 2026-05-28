package nova.publish.bazarbooks.feature.home.model

data class BookUiModel(
    val id: String,
    val title: String,
    val author: String,
    val price: String,
    val imageUrl: String,
    val category: String = "",
    val rating: String = "0.0",
    val description: String = "",
    val stock: Int = 0,
    val isWishlisted: Boolean = false,
)
