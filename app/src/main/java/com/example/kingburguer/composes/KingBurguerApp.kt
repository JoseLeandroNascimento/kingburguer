package com.example.kingburguer.composes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.composes.login.LoginScreen
import com.example.kingburguer.ui.theme.KingburguerTheme

@Composable
fun KingBurguerApp() {
    val navController = rememberNavController()
    KingBurguerNavHost(navController=navController)
}

@Composable
fun KingBurguerNavHost(modifier: Modifier = Modifier,navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.LOGIN.route){
        composable(Screen.LOGIN.route) {
            LoginScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KingBurguerAppPreview() {
    KingburguerTheme {
        KingBurguerApp()
    }
}