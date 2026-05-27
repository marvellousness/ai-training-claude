package nova.publish.bazarbooks.core.navigation

sealed class AppRoute(
    val route: String,
    val showBottomBar: Boolean = false,
    val deepLink: String? = null,
) {
    data object Onboarding : AppRoute("onboarding")
    data object SignIn : AppRoute("signin")
    data object SignUp : AppRoute("signup")
    data object ForgotPassword : AppRoute("forgot_password")
    data object ForgotPasswordCode : AppRoute("forgot_password_code")
    data object CreateNewPassword : AppRoute("create_new_password")

    data object Home : AppRoute("home", showBottomBar = true)
    data object Category : AppRoute("category", showBottomBar = true)
    data object Cart : AppRoute("cart", showBottomBar = true)
    data object Profile : AppRoute("profile", showBottomBar = true)

    data object BookDetail : AppRoute("book_detail/{bookId}", deepLink = "bazarbooks://book/{bookId}") {
        fun create(bookId: String) = "book_detail/$bookId"
    }

    data object VendorList : AppRoute("vendor_list")
    data object AuthorList : AppRoute("author_list")

    data object AuthorDetail : AppRoute("author_detail/{authorId}") {
        fun create(authorId: String) = "author_detail/$authorId"
    }

    data object Search : AppRoute("search")
    data object Checkout : AppRoute("checkout")

    data object AddressEdit : AppRoute("address_edit?addressId={addressId}") {
        fun create(addressId: String?) = addressId?.let { "address_edit/$it" } ?: "address_edit"
    }

    data object PaymentMethod : AppRoute("payment_method")

    data object OrderDetail : AppRoute("order_detail/{orderId}", deepLink = "bazarbooks://order/{orderId}") {
        fun create(orderId: String) = "order_detail/$orderId"
    }

    data object OrderConfirmation : AppRoute("order_confirmation/{orderId}") {
        fun create(orderId: String) = "order_confirmation/$orderId"
    }

    data object Notifications : AppRoute("notifications")
    data object NotificationDetail : AppRoute("notification_detail/{notificationId}", deepLink = "bazarbooks://notification/{notificationId}") {
        fun create(notificationId: String) = "notification_detail/$notificationId"
    }

    data object Orders : AppRoute("orders")
    data object OrderStatus : AppRoute("order_status/{orderId}", deepLink = "bazarbooks://order-status/{orderId}") {
        fun create(orderId: String) = "order_status/$orderId"
    }

    data object ProfileAccount : AppRoute("profile_account")
    data object ProfileAddress : AppRoute("profile_address")
    data object Favorites : AppRoute("favorites")
    data object OrderHistory : AppRoute("order_history")
    data object HelpCenter : AppRoute("help_center")
    data object Offers : AppRoute("offers")

    companion object {
        val bottomTabs: List<AppRoute>
            get() = listOf(Home, Category, Cart, Profile)

        val all: List<AppRoute>
            get() = listOf(
                Onboarding,
                SignIn,
                SignUp,
                ForgotPassword,
                ForgotPasswordCode,
                CreateNewPassword,
                Home,
                Category,
                Cart,
                Profile,
                BookDetail,
                VendorList,
                AuthorList,
                AuthorDetail,
                Search,
                Checkout,
                AddressEdit,
                PaymentMethod,
                OrderDetail,
                OrderConfirmation,
                Notifications,
                NotificationDetail,
                Orders,
                OrderStatus,
                ProfileAccount,
                ProfileAddress,
                Favorites,
                OrderHistory,
                HelpCenter,
                Offers,
            )
    }
}
