package nova.publish.bazarbooks.core.data.local.source

import nova.publish.bazarbooks.core.data.local.dao.BookDao
import nova.publish.bazarbooks.core.data.local.dao.FigmaMetadataDao
import nova.publish.bazarbooks.core.data.local.entity.AuthorEntity
import nova.publish.bazarbooks.core.data.local.entity.BookEntity
import nova.publish.bazarbooks.core.data.local.entity.RecentSearchEntity
import nova.publish.bazarbooks.core.data.local.entity.VendorEntity
import nova.publish.bazarbooks.core.data.local.entity.WishlistEntity
import nova.publish.bazarbooks.core.data.local.mapper.toDomain
import nova.publish.bazarbooks.core.data.repository.model.BookSearchRequest
import nova.publish.bazarbooks.core.domain.model.Author
import nova.publish.bazarbooks.core.domain.model.Book
import nova.publish.bazarbooks.core.domain.model.Vendor

class BookLocalDataSource(
    private val bookDao: BookDao,
    private val figmaMetadataDao: FigmaMetadataDao,
) {
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

    suspend fun getVendors(): List<Vendor> = bookDao.getVendors().map { it.toDomain() }

    suspend fun getAuthors(): List<Author> = bookDao.getAuthors().map { it.toDomain() }

    suspend fun getAuthor(id: String): Author? = bookDao.getAuthor(id)?.toDomain()

    suspend fun getAuthorBooks(authorId: String): List<Book> {
        val author = bookDao.getAuthor(authorId) ?: return emptyList()
        return bookDao.getBooksByAuthorName(author.name).map { entity ->
            entity.toDomain(isWishlisted = bookDao.isWishlisted(entity.id))
        }
    }

    suspend fun toggleWishlist(bookId: String): Boolean {
        val next = !bookDao.isWishlisted(bookId)
        if (next) {
            bookDao.upsertWishlist(WishlistEntity(bookId = bookId, createdAtEpochMillis = System.currentTimeMillis()))
        } else {
            bookDao.deleteWishlist(bookId)
        }
        return next
    }

    suspend fun getRecentSearches(): List<String> = figmaMetadataDao.getRecentSearches().map { it.query }

    suspend fun saveRecentSearch(query: String) {
        val trimmed = query.trim()
        if (trimmed.isNotBlank()) {
            figmaMetadataDao.upsertRecentSearches(
                listOf(RecentSearchEntity(query = trimmed, createdAtEpochMillis = System.currentTimeMillis())),
            )
        }
    }

    suspend fun upsertBooks(books: List<BookEntity>) {
        bookDao.upsertBooks(books)
    }

    suspend fun upsertVendors(vendors: List<VendorEntity>) {
        bookDao.upsertVendors(vendors)
    }

    suspend fun upsertAuthors(authors: List<AuthorEntity>) {
        bookDao.upsertAuthors(authors)
    }
}

private fun VendorEntity.toDomain() = Vendor(
    id = id,
    name = name,
    category = category,
    imageUrl = imageUrl,
)

private fun AuthorEntity.toDomain() = Author(
    id = id,
    name = name,
    role = role,
    bio = bio,
    imageUrl = imageUrl,
)
