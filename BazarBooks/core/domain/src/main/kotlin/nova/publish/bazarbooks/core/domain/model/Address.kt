package nova.publish.bazarbooks.core.domain.model

data class Address(
    val recipient: String,
    val phone: String,
    val line1: String,
    val line2: String? = null,
    val city: String,
    val region: String,
    val postalCode: String? = null,
    val block: String = "",
    val street: String = "",
    val building: String = "",
    val floor: String? = null,
    val flat: String? = null,
    val avenue: String? = null,
)
