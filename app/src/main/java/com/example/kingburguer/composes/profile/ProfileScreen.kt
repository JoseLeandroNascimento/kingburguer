package com.example.kingburguer.composes.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.R
import com.example.kingburguer.ui.theme.KingburguerTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            ProfileProperty(key = R.string.prop_id, value = 3)
            ProfileProperty(key = R.string.prop_name, value = "User A")
            ProfileProperty(key = R.string.prop_email, value = "UserA@gmail.com")
            ProfileProperty(key = R.string.prop_document, value = "027.055.342-88")
            ProfileProperty(key = R.string.prop_birthday, value = "02/02/2000")
        }
    }

}

@Composable
private fun ProfileProperty(
    modifier: Modifier = Modifier,
    @StringRes key: Int,
    value: Any
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = key),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(text = value.toString(), color = MaterialTheme.colorScheme.inverseSurface)
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), thickness = 0.8.dp)
    }

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