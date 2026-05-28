package nova.publish.bazarbooks.core.domain.repository

import nova.publish.bazarbooks.core.domain.model.Book
import nova.publish.bazarbooks.core.domain.model.Author
import nova.publish.bazarbooks.core.domain.model.Vendor

interface BookRepository {
    suspend fun getHomeBooks(): List<Book>
    suspend fun searchBooks(query: String, category: String? = null): List<Book>
    suspend fun getBook(id: String): Book?
    suspend fun getVendors(): List<Vendor>
    suspend fun getAuthors(): List<Author>
    suspend fun getAuthor(id: String): Author?
    suspend fun getAuthorBooks(authorId: String): List<Book>
    suspend fun toggleWishlist(bookId: String): Boolean
    suspend fun getRecentSearches(): List<String>
    suspend fun saveRecentSearch(query: String)
}
