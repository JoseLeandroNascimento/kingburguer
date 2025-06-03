package com.example.kingburguer.composes.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.R
import com.example.kingburguer.ui.theme.KingburguerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text(text = stringResource(id = R.string.login))},
                    navigationIcon = {
                        IconButton(onClick = {  }) {
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
            }
        ) { contentPadding ->
            HomeContentScreen(modifier.padding(top = contentPadding.calculateTopPadding()))
        }
    }
}

@Composable
fun HomeContentScreen(modifier: Modifier = Modifier) {

    Surface(modifier = modifier.fillMaxSize()) {
        Text("Home screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLightPreview() {
    KingburguerTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenDarkPreview() {
    KingburguerTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        HomeScreen()
    }
}
