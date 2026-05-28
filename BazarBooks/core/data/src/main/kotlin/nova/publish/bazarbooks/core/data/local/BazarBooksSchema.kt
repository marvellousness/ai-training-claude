package nova.publish.bazarbooks.core.data.local

object BazarBooksSchema {
    val tableNames = listOf(
        "books",
        "categories",
        "authors",
        "vendors",
        "book_categories",
        "related_books",
        "reviews",
        "wishlist",
        "cart_items",
        "addresses",
        "payment_methods",
        "orders",
        "order_items",
        "order_status_history",
        "notifications",
        "offers",
        "profiles",
        "recent_searches",
        "pending_operations",
    )
}
