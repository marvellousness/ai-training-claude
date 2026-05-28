package nova.publish.bazarbooks.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val vendor: String,
    val priceCents: Long,
    val currencyCode: String,
    val imageUrl: String,
    val category: String,
    val rating: Float?,
    val description: String?,
    val stock: Int,
    val discountPercent: Int,
    val updatedAtEpochMillis: Long,
)

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val sortOrder: Int,
)

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey val id: String,
    val name: String,
    val role: String,
    val bio: String,
    val imageUrl: String,
)

@Entity(tableName = "vendors")
data class VendorEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val imageUrl: String,
)

@Entity(
    tableName = "book_categories",
    primaryKeys = ["bookId", "categoryId"],
    foreignKeys = [
        ForeignKey(BookEntity::class, ["id"], ["bookId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(CategoryEntity::class, ["id"], ["categoryId"], onDelete = ForeignKey.CASCADE),
    ],
    indices = [Index("bookId"), Index("categoryId")],
)
data class BookCategoryCrossRefEntity(
    val bookId: String,
    val categoryId: String,
)

@Entity(
    tableName = "related_books",
    primaryKeys = ["bookId", "relatedBookId"],
    foreignKeys = [
        ForeignKey(BookEntity::class, ["id"], ["bookId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(BookEntity::class, ["id"], ["relatedBookId"], onDelete = ForeignKey.CASCADE),
    ],
    indices = [Index("bookId"), Index("relatedBookId")],
)
data class RelatedBookCrossRefEntity(
    val bookId: String,
    val relatedBookId: String,
)

@Entity(
    tableName = "reviews",
    foreignKeys = [ForeignKey(BookEntity::class, ["id"], ["bookId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("bookId")],
)
data class ReviewEntity(
    @PrimaryKey val id: String,
    val bookId: String,
    val reviewerName: String,
    val rating: Float,
    val body: String,
    val createdAtEpochMillis: Long,
)

@Entity(
    tableName = "wishlist",
    foreignKeys = [ForeignKey(BookEntity::class, ["id"], ["bookId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("bookId")],
)
data class WishlistEntity(
    @PrimaryKey val bookId: String,
    val createdAtEpochMillis: Long,
)
