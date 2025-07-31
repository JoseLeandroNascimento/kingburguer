package com.example.kingburguer.composes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.auth.presentation.login.LoginScreen
import com.example.kingburguer.auth.presentation.login.LoginViewModel
import com.example.kingburguer.auth.presentation.signup.SignupScreen
import com.example.kingburguer.ui.theme.KingburguerTheme

@Composable
fun KingBurguerApp(
    startDestination: Screen
) {

    val navController = rememberNavController()

    KingBurguerNavHost(
        navController = navController,
        startDestination = startDestination
    )


}

@Composable
fun KingBurguerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screen
) {

    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(Screen.LOGIN.route) { navBackStackEntry ->

            val viewModel: LoginViewModel = hiltViewModel(navBackStackEntry)

            LoginScreen(
                loginViewModel = viewModel,
                onSignup = {
                    navController.navigate(Screen.SIGNUP.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.HOME.route) {
                        popUpTo(Screen.LOGIN.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SIGNUP.route) { navBackStack ->
            SignupScreen(
                viewModel = hiltViewModel(navBackStack),
                onNavigationClick = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.MAIN.route) {
            MainScreen(onNavigateToLogin = {
                navController.navigate((Screen.LOGIN.route)) {
                    popUpTo(Screen.LOGIN.route) { inclusive = true }
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KingBurguerAppPreview() {
    KingburguerTheme(
        dynamicColor = false
    ) {
        KingBurguerApp(startDestination = Screen.LOGIN)
    }
}