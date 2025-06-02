package com.example.kingburguer.composes.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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
private fun KingButtonPreview() {
    KingButton(text = "Ola mundo") {}
}