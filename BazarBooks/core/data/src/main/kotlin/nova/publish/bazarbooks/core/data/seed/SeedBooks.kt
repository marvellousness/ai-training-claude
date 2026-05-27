package nova.publish.bazarbooks.core.data.seed

import nova.publish.bazarbooks.core.domain.model.Book

object SeedBooks {
    val books = listOf(
        Book("clean-kotlin", "Clean Kotlin", "A. Reader", 1899, "USD", "", "Engineering", 4.8f, "Practical Kotlin patterns."),
        Book("compose-craft", "Compose Craft", "N. Publisher", 2199, "USD", "", "Android", 4.7f, "Build refined Compose UIs."),
        Book("bookshop-days", "Bookshop Days", "M. Stone", 1499, "USD", "", "Fiction", 4.5f, "A warm novel for readers."),
        Book("product-notes", "Product Notes", "C. Lane", 1699, "USD", "", "Business", 4.4f, "Sharper product thinking."),
    )
}
