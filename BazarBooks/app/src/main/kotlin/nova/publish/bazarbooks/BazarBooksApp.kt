package nova.publish.bazarbooks

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import nova.publish.bazarbooks.core.designsystem.component.BazarBottomBar
import nova.publish.bazarbooks.core.designsystem.component.BazarBottomBarItem
import nova.publish.bazarbooks.core.navigation.AppRoute
import nova.publish.bazarbooks.navigation.AppNavHost

@Composable
fun BazarBooksApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = AppRoute.bottomTabs.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BazarBottomBar(
                    items = listOf(
                        BazarBottomBarItem(
                            label = "Home",
                            selected = currentRoute == AppRoute.Home.route,
                            icon = Icons.Filled.Home,
                            contentDescription = "Home",
                            onClick = { navController.navigateBottomTab(AppRoute.Home) },
                        ),
                        BazarBottomBarItem(
                            label = "Category",
                            selected = currentRoute == AppRoute.Category.route,
                            icon = Icons.Filled.Category,
                            contentDescription = "Category",
                            onClick = { navController.navigateBottomTab(AppRoute.Category) },
                        ),
                        BazarBottomBarItem(
                            label = "Cart",
                            selected = currentRoute == AppRoute.Cart.route,
                            icon = Icons.Filled.ShoppingCart,
                            contentDescription = "Cart",
                            onClick = { navController.navigateBottomTab(AppRoute.Cart) },
                        ),
                        BazarBottomBarItem(
                            label = "Profile",
                            selected = currentRoute == AppRoute.Profile.route,
                            icon = Icons.Filled.Person,
                            contentDescription = "Profile",
                            onClick = { navController.navigateBottomTab(AppRoute.Profile) },
                        ),
                    ),
                )
            }
        },
    ) { innerPadding ->
        AppNavHost(navController = navController, contentPadding = innerPadding)
    }
}

private fun androidx.navigation.NavHostController.navigateBottomTab(route: AppRoute) {
    navigate(route.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
