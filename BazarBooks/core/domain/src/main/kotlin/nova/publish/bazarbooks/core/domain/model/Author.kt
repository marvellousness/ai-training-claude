package nova.publish.bazarbooks.core.domain.model

data class Author(
    val id: String,
    val name: String,
    val role: String,
    val bio: String,
    val imageUrl: String,
)
