package com.example.kingburguer.composes.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.ui.theme.KingburguerTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun ProfileScreenLightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        ProfileScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenDarkPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ProfileScreen()
    }
}