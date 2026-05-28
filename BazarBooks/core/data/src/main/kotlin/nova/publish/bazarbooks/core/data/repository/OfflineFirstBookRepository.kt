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

    private suspend fun refreshBooks() {
        localDataSource.upsertBooks(
            remoteDataSource.fetchBooks().mapIndexed { index, dto ->
                dto.toEntity(updatedAtEpochMillis = index.toLong())
            },
        )
    }
}
