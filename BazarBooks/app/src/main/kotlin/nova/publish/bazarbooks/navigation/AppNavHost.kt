package nova.publish.bazarbooks.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nova.publish.bazarbooks.core.navigation.AppRoute
import nova.publish.bazarbooks.feature.auth.ForgotPasswordRoute
import nova.publish.bazarbooks.feature.auth.SigninRoute
import nova.publish.bazarbooks.feature.auth.SignupRoute
import nova.publish.bazarbooks.feature.cart_checkout.CartRoute
import nova.publish.bazarbooks.feature.cart_checkout.CheckoutRoute
import nova.publish.bazarbooks.feature.cart_checkout.OrderConfirmationRoute
import nova.publish.bazarbooks.feature.home.HomeRoute
import nova.publish.bazarbooks.feature.notifications.NotificationsRoute
import nova.publish.bazarbooks.feature.onboarding.OnboardingRoute
import nova.publish.bazarbooks.feature.orders.OrderStatusRoute
import nova.publish.bazarbooks.feature.profile.ProfileRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = AppRoute.Onboarding.route,
    contentPadding: PaddingValues = PaddingValues(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(contentPadding),
    ) {
        composable(AppRoute.Onboarding.route) {
            OnboardingRoute(onGetStarted = { navController.navigate(AppRoute.SignIn.route) })
        }
        composable(AppRoute.SignIn.route) {
            SigninRoute(
                onSignIn = { navController.navigate(AppRoute.Home.route) },
                onSignUp = { navController.navigate(AppRoute.SignUp.route) },
                onForgotPassword = { navController.navigate(AppRoute.ForgotPassword.route) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(AppRoute.SignUp.route) {
            SignupRoute(
                onSignUp = { navController.navigate(AppRoute.Home.route) },
                onSignIn = { navController.popBackStack() },
            )
        }
        composable(AppRoute.ForgotPassword.route) {
            ForgotPasswordRoute(onBack = { navController.popBackStack() })
        }
        composable(AppRoute.Home.route) {
            HomeRoute(
                onCart = { navController.navigate(AppRoute.Cart.route) },
                onProfile = { navController.navigate(AppRoute.Profile.route) },
                onNotifications = { navController.navigate(AppRoute.Notifications.route) },
            )
        }
        composable(AppRoute.Category.route) {
            Text("Category")
        }
        composable(AppRoute.Cart.route) {
            CartRoute(
                onCheckout = { navController.navigate(AppRoute.Checkout.route) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(AppRoute.Checkout.route) {
            CheckoutRoute(
                onPlaced = { orderId -> navController.navigate(AppRoute.OrderConfirmation.create(orderId)) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(AppRoute.OrderConfirmation.route) { entry ->
            OrderConfirmationRoute(
                orderId = entry.arguments?.getString("orderId").orEmpty(),
                onDone = { navController.navigate(AppRoute.Home.route) },
            )
        }
        composable(AppRoute.Profile.route) {
            ProfileRoute(
                onOrders = { navController.navigate(AppRoute.OrderStatus.create("order-1")) },
                onNotifications = { navController.navigate(AppRoute.Notifications.route) },
                onLogout = { navController.navigate(AppRoute.SignIn.route) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(AppRoute.Notifications.route) {
            NotificationsRoute(
                onBack = { navController.popBackStack() },
                onOrder = { orderId -> navController.navigate(AppRoute.OrderStatus.create(orderId)) },
            )
        }
        composable(AppRoute.OrderStatus.route) { entry ->
            OrderStatusRoute(
                orderId = entry.arguments?.getString("orderId").orEmpty(),
                onBack = { navController.popBackStack() },
            )
        }
    }
}
