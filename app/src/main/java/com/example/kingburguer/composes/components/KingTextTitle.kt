package com.example.kingburguer.composes.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KingTextTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left,
        modifier = modifier.fillMaxWidth().padding(top = 20.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun KingTextTitlePreview () {
    KingTextTitle(
        text = "Ola mundo"
    )
}