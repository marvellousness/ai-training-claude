package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.data.seed.SeedBooks
import nova.publish.bazarbooks.core.domain.model.Author
import nova.publish.bazarbooks.core.domain.model.Vendor
import nova.publish.bazarbooks.core.domain.repository.BookRepository

class FakeBookRepository : BookRepository {
    private val recentSearches = mutableListOf<String>()
    private val wishlist = mutableSetOf<String>()

    override suspend fun getHomeBooks() = SeedBooks.books

    override suspend fun searchBooks(query: String, category: String?) = SeedBooks.books.filter { book ->
        val matchesQuery = query.isBlank() || book.title.contains(query, ignoreCase = true) || book.author.contains(query, ignoreCase = true)
        val matchesCategory = category == null || book.category == category
        matchesQuery && matchesCategory
    }

    override suspend fun getBook(id: String) = SeedBooks.books.firstOrNull { it.id == id }

    override suspend fun getVendors(): List<Vendor> = listOf(
        Vendor("wattpad", "Wattpad", "Books", ""),
        Vendor("kuromi", "Kuromi", "Books", ""),
        Vendor("haymarket", "Haymarket", "Books", ""),
    )

    override suspend fun getAuthors(): List<Author> = listOf(
        Author("john-freeman", "John Freeman", "Writer", "American writer he was the editor of the", ""),
        Author("tess-gunty", "Tess Gunty", "Novelist", "Gunty was born and raised in South Bend, Indiana.", ""),
    )

    override suspend fun getAuthor(id: String): Author? = getAuthors().firstOrNull { it.id == id }

    override suspend fun getAuthorBooks(authorId: String) = SeedBooks.books

    override suspend fun toggleWishlist(bookId: String): Boolean {
        return if (wishlist.contains(bookId)) {
            wishlist.remove(bookId)
            false
        } else {
            wishlist.add(bookId)
            true
        }
    }

    override suspend fun getRecentSearches(): List<String> = recentSearches

    override suspend fun saveRecentSearch(query: String) {
        recentSearches.remove(query)
        recentSearches.add(0, query)
    }
}
