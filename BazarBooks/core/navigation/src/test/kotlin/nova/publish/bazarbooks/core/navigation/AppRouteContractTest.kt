package nova.publish.bazarbooks.core.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppRouteContractTest {

    @Test
    fun `bottom tabs match figma shell destinations`() {
        assertEquals(
            listOf(AppRoute.Home, AppRoute.Category, AppRoute.Cart, AppRoute.Profile),
            AppRoute.bottomTabs,
        )

        assertTrue(AppRoute.Home.showBottomBar)
        assertTrue(AppRoute.Category.showBottomBar)
        assertTrue(AppRoute.Cart.showBottomBar)
        assertTrue(AppRoute.Profile.showBottomBar)
        assertFalse(AppRoute.Onboarding.showBottomBar)
        assertFalse(AppRoute.SignIn.showBottomBar)
    }

    @Test
    fun `figma non tab routes are registered`() {
        val routes = AppRoute.all.map { it.route }.toSet()

        assertTrue("forgot password code route missing", AppRoute.ForgotPasswordCode.route in routes)
        assertTrue("create new password route missing", AppRoute.CreateNewPassword.route in routes)
        assertTrue("book detail route missing", AppRoute.BookDetail.route in routes)
        assertTrue("vendor list route missing", AppRoute.VendorList.route in routes)
        assertTrue("author list route missing", AppRoute.AuthorList.route in routes)
        assertTrue("author detail route missing", AppRoute.AuthorDetail.route in routes)
        assertTrue("search route missing", AppRoute.Search.route in routes)
        assertTrue("address edit route missing", AppRoute.AddressEdit.route in routes)
        assertTrue("payment method route missing", AppRoute.PaymentMethod.route in routes)
        assertTrue("order detail route missing", AppRoute.OrderDetail.route in routes)
        assertTrue("notification detail route missing", AppRoute.NotificationDetail.route in routes)
        assertTrue("profile account route missing", AppRoute.ProfileAccount.route in routes)
        assertTrue("profile address route missing", AppRoute.ProfileAddress.route in routes)
        assertTrue("favorites route missing", AppRoute.Favorites.route in routes)
        assertTrue("order history route missing", AppRoute.OrderHistory.route in routes)
        assertTrue("help center route missing", AppRoute.HelpCenter.route in routes)
        assertTrue("offers route missing", AppRoute.Offers.route in routes)
    }

    @Test
    fun `routes with arguments build primitive id paths and deep links`() {
        assertEquals("book_detail/book-1", AppRoute.BookDetail.create("book-1"))
        assertEquals("author_detail/author-1", AppRoute.AuthorDetail.create("author-1"))
        assertEquals("address_edit", AppRoute.AddressEdit.create(null))
        assertEquals("address_edit/address-1", AppRoute.AddressEdit.create("address-1"))
        assertEquals("order_detail/order-1", AppRoute.OrderDetail.create("order-1"))
        assertEquals("notification_detail/notification-1", AppRoute.NotificationDetail.create("notification-1"))

        assertEquals("bazarbooks://book/{bookId}", AppRoute.BookDetail.deepLink)
        assertEquals("bazarbooks://order/{orderId}", AppRoute.OrderDetail.deepLink)
        assertEquals("bazarbooks://order-status/{orderId}", AppRoute.OrderStatus.deepLink)
        assertEquals("bazarbooks://notification/{notificationId}", AppRoute.NotificationDetail.deepLink)
    }
}
