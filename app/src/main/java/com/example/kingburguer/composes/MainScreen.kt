package com.example.kingburguer.composes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.R
import com.example.kingburguer.composes.home.HomeScreen
import com.example.kingburguer.ui.theme.KingburguerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {

        val navController = rememberNavController()

        Scaffold(
            topBar = {

            },
            bottomBar = {
                MainBottomNavigation(navController = navController)
            }
        ) { contentPadding ->

            MainContentScreen(
                contentPadding = contentPadding,
                navHostController = navController

            )

        }
    }
}

@Composable
fun MainContentScreen(contentPadding: PaddingValues, navHostController: NavHostController) {

    NavHost(
        modifier = Modifier.padding(contentPadding),
        navController = navHostController,
        startDestination = Screen.HOME.route
    ) {
        composable(Screen.HOME.route) {
            HomeScreen(
            )
        }
        composable(Screen.COUPON.route) {
            CouponScreen(
            )
        }
        composable(Screen.PROFILE.route) {
            ProfileScreen(
            )
        }
    }
}


data class NavigationItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val router: Screen
)

@Composable
fun MainBottomNavigation(
    navController: NavHostController
) {


    val navigationItems = listOf(
        NavigationItem(
            title = R.string.menu_home,
            icon = Icons.Default.Home,
            router = Screen.HOME
        ),
        NavigationItem(
            title = R.string.menu_coupon,
            icon = Icons.Default.ShoppingCart,
            router = Screen.COUPON
        ),
        NavigationItem(
            title = R.string.menu_profile,
            icon = Icons.Default.Person,
            router = Screen.PROFILE
        ),
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.navigationBarsPadding(),
    ) {

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        navigationItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.router.route,
                onClick = {
                    if (currentRoute != item.router.route) {
                        navController.navigate(item.router.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = {
                    Text(text = stringResource(id = item.title))
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = "")
                },
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                selectedContentColor = MaterialTheme.colorScheme.primary
            )
        }
    }

}


@Composable
fun CouponScreen(modifier: Modifier = Modifier) {

    Surface(modifier = modifier.fillMaxSize()) {
        Text("Coupon screen", style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    Surface(modifier = modifier.fillMaxSize()) {
        Text("Profile screen", style = MaterialTheme.typography.headlineLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenLightPreview() {
    KingburguerTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenDarkPreview() {
    KingburguerTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        MainScreen()
    }
}
