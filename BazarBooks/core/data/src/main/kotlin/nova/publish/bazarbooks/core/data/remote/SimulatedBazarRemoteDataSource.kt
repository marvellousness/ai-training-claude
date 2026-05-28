package nova.publish.bazarbooks.core.data.remote

import nova.publish.bazarbooks.core.data.local.entity.AuthorEntity
import nova.publish.bazarbooks.core.data.local.entity.VendorEntity
import nova.publish.bazarbooks.core.data.remote.dto.BookDto

class SimulatedBazarRemoteDataSource {
    suspend fun fetchBooks(): List<BookDto> = FigmaBookDtos.items
    suspend fun fetchVendors(): List<VendorEntity> = FigmaCatalogDtos.vendors
    suspend fun fetchAuthors(): List<AuthorEntity> = FigmaCatalogDtos.authors
}

object FigmaBookDtos {
    val items = listOf(
        BookDto("kite-runner", "The Kite Runner", "Khaled Hosseini", "Bazar", 3999, "KWD", "", "Novels", 4.0f, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Viverra dignissim ac ac ac. Nibh et sed ac, eget malesuada.", 12, 25),
        BookDto("da-vinci-code", "The Da vinci Code", "Dan Brown", "Wattpad", 1999, "KWD", "", "Novels", 4.3f, "A bestselling mystery thriller.", 8, 0),
        BookDto("carrie-fisher", "Carrie Fisher", "Tess Gunty", "Kuromi", 2712, "KWD", "", "Novels", 4.0f, "A Figma category product sample.", 6, 0),
        BookDto("good-sister", "The Good Sister", "Sally Hepworth", "Haymarket", 2712, "KWD", "", "Self Love", 4.1f, "A tense relationship story.", 7, 10),
        BookDto("the-waiting", "The Waiting", "Michael Connelly", "Jstor", 2712, "KWD", "", "Science", 4.2f, "A sharp investigative read.", 5, 23),
        BookDto("where-are-you", "Where Are You", "Adam Dalva", "Peloton", 2712, "KWD", "", "Romantic", 3.9f, "A category-grid Figma sample.", 4, 0),
        BookDto("bright-young-women", "Bright Young Women", "Jessica Knoll", "Crane & Co", 2712, "KWD", "", "Romantic", 4.0f, "A polished contemporary novel.", 4, 0),
    )
}

object FigmaCatalogDtos {
    val vendors = listOf(
        VendorEntity("wattpad", "Wattpad", "Books", ""),
        VendorEntity("kuromi", "Kuromi", "Books", ""),
        VendorEntity("crane-co", "Crane & Co", "Stationary", ""),
        VendorEntity("gooday", "GooDay", "Special for you", ""),
        VendorEntity("warehouse", "Warehouse", "Books", ""),
        VendorEntity("peppa-pig", "Peppa Pig", "Poems", ""),
        VendorEntity("jstor", "Jstor", "Books", ""),
        VendorEntity("peloton", "Peloton", "Special for you", ""),
        VendorEntity("haymarket", "Haymarket", "Books", ""),
    )

    val authors = listOf(
        AuthorEntity("john-freeman", "John Freeman", "Writer", "American writer he was the editor of the", ""),
        AuthorEntity("adam-dalva", "Adam Dalva", "Journalist", "He is the senior fiction editor of Guernica magazine.", ""),
        AuthorEntity("abraham-verghese", "Abraham verghese", "Novelist", "He is the professor and Linda R. Meier and Joan F. Lane Provostial Professor.", ""),
        AuthorEntity("tess-gunty", "Tess Gunty", "Novelist", "Gunty was born and raised in South Bend, Indiana. She graduated from the University of Notre Dame with a Bachelor of Arts in English and from New York University.", ""),
        AuthorEntity("ann-napolitano", "Ann Napolitano", "Novelist", "She is the author of the novels A Good Hard Look and Hello Beautiful.", ""),
        AuthorEntity("hernan-diaz", "Hernan Diaz", "Writer", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", ""),
    )
}
