package com.example.kingburguer.composes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.ui.theme.KingburguerTheme


@Composable
fun KingButton(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            enabled = enable && !loading,
            shape = RoundedCornerShape(10),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if(!loading){
                Text(text = text.uppercase())
            }
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KingButtonLightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        Column {
            KingButton(text = "Ola mundo", enable = false) {}
            KingButton(text = "Ola mundo", enable = true) {}
            KingButton(text = "Ola mundo", loading = true) {}
            KingButton(text = "Ola mundo", loading = false) {}
        }
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