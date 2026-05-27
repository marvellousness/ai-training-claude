package nova.publish.bazarbooks.core.domain.model

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val avatarUrl: String = "",
    val phone: String? = null,
)
