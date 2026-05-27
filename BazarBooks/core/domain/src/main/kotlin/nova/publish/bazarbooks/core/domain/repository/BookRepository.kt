package nova.publish.bazarbooks.core.domain.repository

import nova.publish.bazarbooks.core.domain.model.Book

interface BookRepository {
    suspend fun getHomeBooks(): List<Book>
    suspend fun searchBooks(query: String, category: String? = null): List<Book>
    suspend fun getBook(id: String): Book?
}
