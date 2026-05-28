package nova.publish.bazarbooks.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val type: String,
    val isRead: Boolean,
    val createdAtEpochMillis: Long,
    val orderId: String?,
)

@Entity(tableName = "offers")
data class OfferEntity(
    @PrimaryKey val id: String,
    val title: String,
    val discountPercent: Int,
    val code: String,
    val colorName: String,
)

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val imageUrl: String?,
)

@Entity(tableName = "recent_searches")
data class RecentSearchEntity(
    @PrimaryKey val query: String,
    val createdAtEpochMillis: Long,
)

@Entity(tableName = "pending_operations")
data class PendingOperationEntity(
    @PrimaryKey val id: String,
    val type: String,
    val payload: String,
    val createdAtEpochMillis: Long,
    val retryCount: Int,
)
