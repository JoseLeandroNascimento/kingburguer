package com.example.kingburguer.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.kingburguer.composes.KingBurguerApp
import com.example.kingburguer.composes.Screen
import com.example.kingburguer.ui.theme.KingburguerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.hasSession.value == null
            }
        }
        setContent {
            KingburguerTheme(dynamicColor = false) {

                val startState = viewModel.hasSession.collectAsState()

                startState.value?.let { logged ->

                    val startDestination = if (logged) Screen.MAIN else Screen.LOGIN
                    KingBurguerApp(startDestination)

                }
            }
        }
    }
}