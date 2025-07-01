package com.example.kingburguer.composes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.composes.login.LoginScreen
import com.example.kingburguer.composes.signup.SignupScreen
import com.example.kingburguer.ui.theme.KingburguerTheme
import com.example.kingburguer.viewmodels.SplashViewModel

@Composable
fun KingBurguerApp(
    viewModel: SplashViewModel = viewModel(factory = SplashViewModel.factory)
) {
    val navController = rememberNavController()

    val hasSessionState = viewModel.hasSessionState.collectAsState(null)

    hasSessionState.value?.let { logged ->

        val startDestination = if (logged) Screen.MAIN else Screen.LOGIN
        KingBurguerNavHost(
            navController = navController,
            startDestination = startDestination
        )
    }

}

@Composable
fun KingBurguerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screen
) {

    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(Screen.LOGIN.route) {
            LoginScreen(
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

        composable(Screen.SIGNUP.route) {
            SignupScreen(
                onNavigationClick = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.MAIN.route) {
            MainScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KingBurguerAppPreview() {
    KingburguerTheme(
        dynamicColor = false
    ) {
        KingBurguerApp()
    }
}