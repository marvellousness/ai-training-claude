package nova.publish.bazarbooks.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import nova.publish.bazarbooks.core.data.local.entity.AddressEntity
import nova.publish.bazarbooks.core.data.local.entity.NotificationEntity
import nova.publish.bazarbooks.core.data.local.entity.OfferEntity
import nova.publish.bazarbooks.core.data.local.entity.OrderEntity
import nova.publish.bazarbooks.core.data.local.entity.OrderItemEntity
import nova.publish.bazarbooks.core.data.local.entity.OrderStatusHistoryEntity
import nova.publish.bazarbooks.core.data.local.entity.PaymentMethodEntity
import nova.publish.bazarbooks.core.data.local.entity.PendingOperationEntity
import nova.publish.bazarbooks.core.data.local.entity.ProfileEntity
import nova.publish.bazarbooks.core.data.local.entity.RecentSearchEntity

@Dao
interface FigmaMetadataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAddresses(items: List<AddressEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPaymentMethods(items: List<PaymentMethodEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOrders(items: List<OrderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOrderItems(items: List<OrderItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOrderStatusHistory(items: List<OrderStatusHistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNotifications(items: List<NotificationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOffers(items: List<OfferEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfiles(items: List<ProfileEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecentSearches(items: List<RecentSearchEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPendingOperations(items: List<PendingOperationEntity>)
}
