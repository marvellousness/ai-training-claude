package nova.publish.bazarbooks.core.data.remote

import nova.publish.bazarbooks.core.data.remote.dto.BookDto

class SimulatedBazarRemoteDataSource {
    suspend fun fetchBooks(): List<BookDto> = FigmaBookDtos.items
}

object FigmaBookDtos {
    val items = listOf(
        BookDto("kite-runner", "The Kite Runner", "Khaled Hosseini", "Bazar", 3999, "KWD", "", "Novels", 4.0f, "A moving Figma detail-screen sample.", 12, 25),
        BookDto("da-vinci-code", "The Da vinci Code", "Dan Brown", "Wattpad", 1999, "KWD", "", "Novels", 4.3f, "A bestselling mystery thriller.", 8, 0),
        BookDto("good-sister", "The Good Sister", "Sally Hepworth", "Haymarket", 2712, "KWD", "", "Self Love", 4.1f, "A tense relationship story.", 7, 10),
        BookDto("the-waiting", "The Waiting", "Michael Connelly", "Jstor", 2712, "KWD", "", "Science", 4.2f, "A sharp investigative read.", 5, 23),
        BookDto("bright-young-women", "Bright Young Women", "Jessica Knoll", "Crane & Co", 2712, "KWD", "", "Romantic", 4.0f, "A polished contemporary novel.", 4, 0),
    )
}
