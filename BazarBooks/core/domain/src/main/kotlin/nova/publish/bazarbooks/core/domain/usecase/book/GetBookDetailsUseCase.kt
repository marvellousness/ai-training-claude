package nova.publish.bazarbooks.core.domain.usecase.book

import nova.publish.bazarbooks.core.domain.repository.BookRepository

class GetBookDetailsUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: String) = repository.getBook(id)
}
