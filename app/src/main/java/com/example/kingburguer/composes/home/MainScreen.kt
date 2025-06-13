package com.example.kingburguer.composes.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.R
import com.example.kingburguer.composes.Screen
import com.example.kingburguer.ui.theme.KingburguerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.login)) },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                )
            },
            bottomBar = {
                MainBottomNavigation()
            }
        ) { contentPadding ->

            Text("Teste", modifier.padding(contentPadding))

        }
    }
}


data class NavigationItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val router: Screen
)

@Composable
fun MainBottomNavigation(modifier: Modifier = Modifier) {

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
        )
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                selected = true,
                onClick = {},
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
fun HomeScreen(modifier: Modifier = Modifier) {

    Surface(modifier = modifier.fillMaxSize()) {
        Text("Home screen", style = MaterialTheme.typography.headlineLarge)
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
