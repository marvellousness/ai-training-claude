package nova.publish.bazarbooks.core.domain.usecase.book

import nova.publish.bazarbooks.core.domain.repository.BookRepository

class SearchBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(query: String, category: String? = null) = repository.searchBooks(query, category)
}
