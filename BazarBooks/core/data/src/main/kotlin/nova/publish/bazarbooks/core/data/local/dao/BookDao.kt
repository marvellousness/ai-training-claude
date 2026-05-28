package nova.publish.bazarbooks.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nova.publish.bazarbooks.core.data.local.entity.AuthorEntity
import nova.publish.bazarbooks.core.data.local.entity.BookCategoryCrossRefEntity
import nova.publish.bazarbooks.core.data.local.entity.BookEntity
import nova.publish.bazarbooks.core.data.local.entity.CategoryEntity
import nova.publish.bazarbooks.core.data.local.entity.RelatedBookCrossRefEntity
import nova.publish.bazarbooks.core.data.local.entity.ReviewEntity
import nova.publish.bazarbooks.core.data.local.entity.VendorEntity
import nova.publish.bazarbooks.core.data.local.entity.WishlistEntity

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY updatedAtEpochMillis DESC")
    fun observeHomeBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books ORDER BY updatedAtEpochMillis DESC")
    suspend fun getHomeBooks(): List<BookEntity>

    @Query(
        """
        SELECT * FROM books
        WHERE (:query = '' OR title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%')
        AND (:category IS NULL OR category = :category)
        ORDER BY
            CASE WHEN :sort = 'PRICE_ASC' THEN priceCents END ASC,
            CASE WHEN :sort = 'PRICE_DESC' THEN priceCents END DESC,
            CASE WHEN :sort = 'RATING_DESC' THEN rating END DESC,
            updatedAtEpochMillis DESC
        LIMIT :limit OFFSET :offset
        """,
    )
    suspend fun searchBooks(query: String, category: String?, sort: String, limit: Int, offset: Int): List<BookEntity>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBook(id: String): BookEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE bookId = :bookId)")
    suspend fun isWishlisted(bookId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBooks(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategories(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAuthors(authors: List<AuthorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertVendors(vendors: List<VendorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBookCategories(crossRefs: List<BookCategoryCrossRefEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRelatedBooks(crossRefs: List<RelatedBookCrossRefEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertReviews(reviews: List<ReviewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWishlist(item: WishlistEntity)

    @Query("DELETE FROM wishlist WHERE bookId = :bookId")
    suspend fun deleteWishlist(bookId: String)
}
