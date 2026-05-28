package nova.publish.bazarbooks.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nova.publish.bazarbooks.core.data.local.entity.CartItemEntity
import nova.publish.bazarbooks.core.data.local.model.CartItemWithBook

@Dao
interface CartDao {
    @Transaction
    @Query(
        """
        SELECT
            cart_items.bookId AS bookId,
            cart_items.quantity AS quantity,
            cart_items.updatedAtEpochMillis AS updatedAtEpochMillis,
            books.id AS book_id,
            books.title AS book_title,
            books.author AS book_author,
            books.vendor AS book_vendor,
            books.priceCents AS book_priceCents,
            books.currencyCode AS book_currencyCode,
            books.imageUrl AS book_imageUrl,
            books.category AS book_category,
            books.rating AS book_rating,
            books.description AS book_description,
            books.stock AS book_stock,
            books.discountPercent AS book_discountPercent,
            books.updatedAtEpochMillis AS book_updatedAtEpochMillis
        FROM cart_items
        INNER JOIN books ON books.id = cart_items.bookId
        ORDER BY cart_items.updatedAtEpochMillis DESC
        """,
    )
    fun observeCart(): Flow<List<CartItemWithBook>>

    @Query("SELECT * FROM cart_items WHERE bookId = :bookId")
    suspend fun getCartItem(bookId: String): CartItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCartItem(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE bookId = :bookId")
    suspend fun remove(bookId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}
