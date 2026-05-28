package nova.publish.bazarbooks.feature.home.model

data class OfferUiModel(
    val title: String = "Special Offer",
    val subtitle: String = "Discount 25%",
    val actionLabel: String = "Order Now",
)

data class VendorUiModel(
    val id: String,
    val name: String,
    val category: String,
    val imageUrl: String,
)

data class AuthorUiModel(
    val id: String,
    val name: String,
    val role: String,
    val bio: String,
    val imageUrl: String,
)

data class LocationUiModel(
    val name: String = "",
    val phone: String = "",
    val governorate: String = "",
    val city: String = "",
    val block: String = "",
    val street: String = "",
    val building: String = "",
    val floor: String = "",
    val flat: String = "",
    val avenue: String = "",
) {
    val line1: String
        get() = listOf(street, building).filter { it.isNotBlank() }.joinToString(", ")

    val fullAddress: String
        get() = listOf(block, street, building, floor, flat, avenue, city, governorate)
            .filter { it.isNotBlank() }
            .joinToString(", ")
}
