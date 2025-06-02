package com.example.kingburguer.composes.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.ui.theme.KingburguerTheme

@Composable
fun KingButton(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enable,
        shape = RoundedCornerShape(10)
    ) {
        Text(text = text.uppercase())
    }
}

@Preview(showBackground = true)
@Composable
private fun KingButtonLightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        KingButton(text = "Ola mundo") {}
    }
}

@Preview(showBackground = true)
@Composable
private fun KingButtonDarkPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        KingButton(text = "Ola mundo") {}
    }
}