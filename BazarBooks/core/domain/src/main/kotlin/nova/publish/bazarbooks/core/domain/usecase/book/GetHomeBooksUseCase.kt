package nova.publish.bazarbooks.core.domain.usecase.book

import nova.publish.bazarbooks.core.domain.repository.BookRepository

class GetHomeBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke() = repository.getHomeBooks()
}
