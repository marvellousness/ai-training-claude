package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.data.seed.SeedBooks
import nova.publish.bazarbooks.core.domain.repository.BookRepository

class FakeBookRepository : BookRepository {
    override suspend fun getHomeBooks() = SeedBooks.books

    override suspend fun searchBooks(query: String, category: String?) = SeedBooks.books.filter { book ->
        val matchesQuery = query.isBlank() || book.title.contains(query, ignoreCase = true) || book.author.contains(query, ignoreCase = true)
        val matchesCategory = category == null || book.category == category
        matchesQuery && matchesCategory
    }

    override suspend fun getBook(id: String) = SeedBooks.books.firstOrNull { it.id == id }
}
