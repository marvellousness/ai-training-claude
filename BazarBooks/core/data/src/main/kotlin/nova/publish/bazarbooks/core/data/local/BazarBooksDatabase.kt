package nova.publish.bazarbooks.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import nova.publish.bazarbooks.core.data.local.dao.BookDao
import nova.publish.bazarbooks.core.data.local.dao.CartDao
import nova.publish.bazarbooks.core.data.local.dao.FigmaMetadataDao
import nova.publish.bazarbooks.core.data.local.entity.AddressEntity
import nova.publish.bazarbooks.core.data.local.entity.AuthorEntity
import nova.publish.bazarbooks.core.data.local.entity.BookCategoryCrossRefEntity
import nova.publish.bazarbooks.core.data.local.entity.BookEntity
import nova.publish.bazarbooks.core.data.local.entity.CartItemEntity
import nova.publish.bazarbooks.core.data.local.entity.CategoryEntity
import nova.publish.bazarbooks.core.data.local.entity.NotificationEntity
import nova.publish.bazarbooks.core.data.local.entity.OfferEntity
import nova.publish.bazarbooks.core.data.local.entity.OrderEntity
import nova.publish.bazarbooks.core.data.local.entity.OrderItemEntity
import nova.publish.bazarbooks.core.data.local.entity.OrderStatusHistoryEntity
import nova.publish.bazarbooks.core.data.local.entity.PaymentMethodEntity
import nova.publish.bazarbooks.core.data.local.entity.PendingOperationEntity
import nova.publish.bazarbooks.core.data.local.entity.ProfileEntity
import nova.publish.bazarbooks.core.data.local.entity.RecentSearchEntity
import nova.publish.bazarbooks.core.data.local.entity.RelatedBookCrossRefEntity
import nova.publish.bazarbooks.core.data.local.entity.ReviewEntity
import nova.publish.bazarbooks.core.data.local.entity.VendorEntity
import nova.publish.bazarbooks.core.data.local.entity.WishlistEntity

@Database(
    entities = [
        BookEntity::class,
        CategoryEntity::class,
        AuthorEntity::class,
        VendorEntity::class,
        BookCategoryCrossRefEntity::class,
        RelatedBookCrossRefEntity::class,
        ReviewEntity::class,
        WishlistEntity::class,
        CartItemEntity::class,
        AddressEntity::class,
        PaymentMethodEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        OrderStatusHistoryEntity::class,
        NotificationEntity::class,
        OfferEntity::class,
        ProfileEntity::class,
        RecentSearchEntity::class,
        PendingOperationEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class BazarBooksDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun cartDao(): CartDao
    abstract fun figmaMetadataDao(): FigmaMetadataDao
}
