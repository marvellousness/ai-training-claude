package nova.publish.bazarbooks.core.data.local.source

import nova.publish.bazarbooks.core.data.local.dao.BookDao
import nova.publish.bazarbooks.core.data.local.entity.BookEntity
import nova.publish.bazarbooks.core.data.local.mapper.toDomain
import nova.publish.bazarbooks.core.data.repository.model.BookSearchRequest
import nova.publish.bazarbooks.core.domain.model.Book

class BookLocalDataSource(private val bookDao: BookDao) {
    suspend fun getHomeBooks(): List<Book> = bookDao.getHomeBooks().map { entity ->
        entity.toDomain(isWishlisted = bookDao.isWishlisted(entity.id))
    }

    suspend fun searchBooks(request: BookSearchRequest): List<Book> = bookDao.searchBooks(
        query = request.query,
        category = request.category,
        sort = request.roomSortKey,
        limit = request.pageSize,
        offset = request.offset,
    ).map { entity ->
        entity.toDomain(isWishlisted = bookDao.isWishlisted(entity.id))
    }

    suspend fun getBook(id: String): Book? = bookDao.getBook(id)?.let { entity ->
        entity.toDomain(isWishlisted = bookDao.isWishlisted(entity.id))
    }

    suspend fun upsertBooks(books: List<BookEntity>) {
        bookDao.upsertBooks(books)
    }
}
