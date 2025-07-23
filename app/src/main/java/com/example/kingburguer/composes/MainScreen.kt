package com.example.kingburguer.composes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kingburguer.R
import com.example.kingburguer.composes.home.HomeScreen
import com.example.kingburguer.composes.product.ProductScreen
import com.example.kingburguer.composes.profile.ProfileScreen
import com.example.kingburguer.ui.theme.KingburguerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var titleTopBarId by remember { mutableIntStateOf(R.string.menu_home) }

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute == Screen.HOME.route) {
            titleTopBarId = R.string.menu_home
        }
    }

    Surface(
        modifier = modifier.fillMaxSize()
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = titleTopBarId),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.logo),
                            tint = Color.Unspecified,
                            contentDescription = ""
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.PowerSettingsNew,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                )
            },
            bottomBar = {
                MainBottomNavigation(navController = navController) { titleId ->

                    titleTopBarId = titleId
                }
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
        navController = navHostController,
        startDestination = Screen.HOME.route
    ) {
        composable(Screen.HOME.route) {
            HomeScreen(
                modifier = Modifier.padding(contentPadding),
            ) { productId ->
                navHostController.navigate("${Screen.PRODUCT.route}/$productId")
            }
        }
        composable(Screen.COUPON.route) {
            CouponScreen(modifier = Modifier.padding(contentPadding))
        }
        composable(Screen.PROFILE.route) {
            ProfileScreen(modifier = Modifier.padding(contentPadding))
        }
        composable(
            route = "${Screen.PRODUCT.route}/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) {
            ProductScreen(modifier = Modifier.padding(contentPadding))
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
    navController: NavHostController,
    onNavigationChange: (Int) -> Unit
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

                        onNavigationChange(item.title)
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
