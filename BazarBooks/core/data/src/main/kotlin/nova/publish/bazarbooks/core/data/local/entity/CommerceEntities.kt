package nova.publish.bazarbooks.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    foreignKeys = [ForeignKey(BookEntity::class, ["id"], ["bookId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("bookId")],
)
data class CartItemEntity(
    @PrimaryKey val bookId: String,
    val quantity: Int,
    val updatedAtEpochMillis: Long,
)

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey val id: String,
    val recipient: String,
    val phone: String,
    val governorate: String,
    val city: String,
    val block: String,
    val street: String,
    val building: String,
    val floor: String?,
    val flat: String?,
    val avenue: String?,
    val label: String,
)

@Entity(tableName = "payment_methods")
data class PaymentMethodEntity(
    @PrimaryKey val id: String,
    val type: String,
    val displayName: String,
    val isSelected: Boolean,
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val totalCents: Long,
    val currencyCode: String,
    val status: String,
    val createdAtEpochMillis: Long,
    val addressId: String,
    val paymentMethodId: String,
)

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(OrderEntity::class, ["id"], ["orderId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(BookEntity::class, ["id"], ["bookId"], onDelete = ForeignKey.CASCADE),
    ],
    indices = [Index("orderId"), Index("bookId")],
)
data class OrderItemEntity(
    @PrimaryKey val id: String,
    val orderId: String,
    val bookId: String,
    val quantity: Int,
    val priceCents: Long,
)

@Entity(
    tableName = "order_status_history",
    foreignKeys = [ForeignKey(OrderEntity::class, ["id"], ["orderId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("orderId")],
)
data class OrderStatusHistoryEntity(
    @PrimaryKey val id: String,
    val orderId: String,
    val status: String,
    val createdAtEpochMillis: Long,
)
