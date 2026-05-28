package nova.publish.bazarbooks.core.data.repository

import nova.publish.bazarbooks.core.data.local.mapper.toEntity
import nova.publish.bazarbooks.core.data.local.source.BookLocalDataSource
import nova.publish.bazarbooks.core.data.remote.SimulatedBazarRemoteDataSource
import nova.publish.bazarbooks.core.data.repository.model.BookSearchRequest
import nova.publish.bazarbooks.core.domain.repository.BookRepository

class OfflineFirstBookRepository(
    private val localDataSource: BookLocalDataSource,
    private val remoteDataSource: SimulatedBazarRemoteDataSource,
) : BookRepository {
    override suspend fun getHomeBooks() = localDataSource.getHomeBooks().ifEmpty {
        refreshBooks()
        localDataSource.getHomeBooks()
    }

    override suspend fun searchBooks(query: String, category: String?) = localDataSource.searchBooks(
        BookSearchRequest(query = query, category = category),
    ).ifEmpty {
        refreshBooks()
        localDataSource.searchBooks(BookSearchRequest(query = query, category = category))
    }

    override suspend fun getBook(id: String) = localDataSource.getBook(id) ?: run {
        refreshBooks()
        localDataSource.getBook(id)
    }

    override suspend fun getVendors() = localDataSource.getVendors().ifEmpty {
        refreshCatalogMetadata()
        localDataSource.getVendors()
    }

    override suspend fun getAuthors() = localDataSource.getAuthors().ifEmpty {
        refreshCatalogMetadata()
        localDataSource.getAuthors()
    }

    override suspend fun getAuthor(id: String) = localDataSource.getAuthor(id) ?: run {
        refreshCatalogMetadata()
        localDataSource.getAuthor(id)
    }

    override suspend fun getAuthorBooks(authorId: String) = localDataSource.getAuthorBooks(authorId).ifEmpty {
        refreshBooks()
        localDataSource.getAuthorBooks(authorId)
    }

    override suspend fun toggleWishlist(bookId: String) = localDataSource.toggleWishlist(bookId)

    override suspend fun getRecentSearches() = localDataSource.getRecentSearches()

    override suspend fun saveRecentSearch(query: String) = localDataSource.saveRecentSearch(query)

    private suspend fun refreshBooks() {
        localDataSource.upsertBooks(
            remoteDataSource.fetchBooks().mapIndexed { index, dto ->
                dto.toEntity(updatedAtEpochMillis = index.toLong())
            },
        )
    }

    private suspend fun refreshCatalogMetadata() {
        localDataSource.upsertVendors(remoteDataSource.fetchVendors())
        localDataSource.upsertAuthors(remoteDataSource.fetchAuthors())
    }
}
