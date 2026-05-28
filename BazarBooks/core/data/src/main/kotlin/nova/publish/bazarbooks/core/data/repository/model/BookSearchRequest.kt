package nova.publish.bazarbooks.core.data.repository.model

data class BookSearchRequest(
    val query: String = "",
    val category: String? = null,
    val author: String? = null,
    val minPriceCents: Long? = null,
    val maxPriceCents: Long? = null,
    val minRating: Float? = null,
    val sort: Sort = Sort.Relevance,
    val page: Int = 1,
    val pageSize: Int = 20,
) {
    enum class Sort {
        Relevance,
        PriceAsc,
        PriceDesc,
        RatingDesc,
    }

    val hasFilters: Boolean
        get() = query.isNotBlank() ||
            category != null ||
            author != null ||
            minPriceCents != null ||
            maxPriceCents != null ||
            minRating != null

    val offset: Int
        get() = (page.coerceAtLeast(1) - 1) * pageSize.coerceAtLeast(1)

    val roomSortKey: String
        get() = when (sort) {
            Sort.PriceAsc -> "PRICE_ASC"
            Sort.PriceDesc -> "PRICE_DESC"
            Sort.RatingDesc -> "RATING_DESC"
            Sort.Relevance -> "RELEVANCE"
        }
}
